package michal.malek.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepoDto {
    public String name;
    public OwnerDto owner;
    public boolean fork;

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OwnerDto {
        public String login;
    }
}
