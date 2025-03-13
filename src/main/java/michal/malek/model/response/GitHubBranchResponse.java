package michal.malek.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GitHubBranchResponse {
    public String name;
    public String lastCommitSha;
}
