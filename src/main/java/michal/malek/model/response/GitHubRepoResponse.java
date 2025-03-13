package michal.malek.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GitHubRepoResponse {
    public String repositoryName;
    public String ownerLogin;
    public List<GitHubBranchResponse> branches;
}
