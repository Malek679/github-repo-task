package michal.malek.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import michal.malek.model.dto.GitHubBranchDto;
import michal.malek.model.dto.GitHubRepoDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.github.com")
@Produces(MediaType.APPLICATION_JSON)
public interface GitHubApiService {

    @GET
    @Path("/users/{user}/repos")
    Uni<List<GitHubRepoDto>> getUserRepos(@PathParam("user") String user,
                                          @QueryParam("type") String type);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<GitHubBranchDto>> getBranches(@PathParam("owner") String owner,
                                           @PathParam("repo")  String repo);
}
