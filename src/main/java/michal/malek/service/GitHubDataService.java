package michal.malek.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import michal.malek.exception.GitHubApiException;
import michal.malek.exception.GitHubNoRepositoriesException;
import michal.malek.exception.GitHubUserNotFoundException;
import michal.malek.model.dto.GitHubRepoDto;
import michal.malek.model.message.ErrorMessages;
import michal.malek.model.response.GitHubBranchResponse;
import michal.malek.model.response.GitHubRepoResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
@ApplicationScoped
public class GitHubDataService {

    @Inject
    @RestClient
    GitHubApiService gitHubService;

    public Uni<List<GitHubRepoResponse>> getNonForkReposWithBranches(String user, String type) {
        return gitHubService.getUserRepos(user, type)
                .onFailure(WebApplicationException.class)
                .transform(ex -> handleApiException((WebApplicationException) ex, user))
                .onItem().transform(this::filterForks)
                .onItem().transformToUni(repos -> {
                    if (repos == null || repos.isEmpty()) {
                        return Uni.createFrom().failure(new GitHubNoRepositoriesException(user));
                    }
                    return fetchBranchesForAll(repos);
                });
    }

    private List<GitHubRepoDto> filterForks(List<GitHubRepoDto> repos) {
        if (repos == null) {
            return List.of();
        }
        return repos.stream().filter(r -> !r.fork).toList();
    }

    private Uni<List<GitHubRepoResponse>> fetchBranchesForAll(List<GitHubRepoDto> repos) {
        List<Uni<GitHubRepoResponse>> unis = repos.stream()
                .map(this::fetchBranches)
                .toList();
        return Uni.join().all(unis)
                .andFailFast()
                .onFailure().transform(ex -> new GitHubApiException(ErrorMessages.GENERAL_FETCH_ERROR));
    }

    private Uni<GitHubRepoResponse> fetchBranches(GitHubRepoDto repo) {
        return gitHubService.getBranches(repo.owner.login, repo.name)
                .onFailure(WebApplicationException.class)
                .transform(ex -> new GitHubApiException(String.format(ErrorMessages.FETCH_BRANCHES_ERROR, repo.name)))
                .onFailure().transform(ex -> new GitHubApiException(String.format(ErrorMessages.UNEXPECTED_ERROR)))
                .onItem().transform(branchList -> {
                    var branches = branchList.stream()
                            .map(b -> new GitHubBranchResponse(b.name, b.commit.sha))
                            .toList();
                    return new GitHubRepoResponse(repo.name, repo.owner.login, branches);
                });
    }

    private RuntimeException handleApiException(WebApplicationException ex, String user) {
        int statusCode = ex.getResponse().getStatus();
        return switch (statusCode) {
            case 404 -> new GitHubUserNotFoundException(user);
            case 400 -> new GitHubApiException(String.format(ErrorMessages.INVALID_INPUT, ex.getMessage()));
            default -> new GitHubApiException(ErrorMessages.GITHUB_API_ERROR);
        };
    }
}
