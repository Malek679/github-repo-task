package michal.malek.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GitHubRepoRequest {
    private String username;
    private String type;
}
