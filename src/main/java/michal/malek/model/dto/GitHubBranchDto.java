package michal.malek.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubBranchDto {
    public String name;
    public CommitDto commit;

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommitDto {
        public String sha;
    }
}
