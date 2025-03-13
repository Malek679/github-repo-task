package michal.malek;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import michal.malek.model.response.GitHubBranchResponse;
import michal.malek.model.response.GitHubRepoResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GitHubControllerIT {

    private static final String BASE_PATH = "/api/github/repos";
    private static final String USER_PARAM = "user";
    private static final String TEST_USER = "Michaaaal";
    private static final String MASTER_BRANCH = "master";
    private static final String EXPECTED_SHA = "22fa90c043a7250e12bdd3e1a9fa8395ab42cd74";

    @Test
    public void testGetNonForkRepos() {
        List<GitHubRepoResponse> repos = given()
                .queryParam(USER_PARAM, TEST_USER)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().jsonPath().getList("", GitHubRepoResponse.class);

        assertNotNull(repos);
        assertFalse(repos.isEmpty());

        for (GitHubRepoResponse repo : repos) {
            assertNotNull(repo.getRepositoryName());
            assertEquals(TEST_USER, repo.getOwnerLogin());

            assertNotNull(repo.getBranches());
            for (GitHubBranchResponse branch : repo.getBranches()) {
                assertNotNull(branch.getName());
                assertNotNull(branch.getLastCommitSha());
            }
        }

        assertTrue(repos.stream()
                .flatMap(repo -> repo.getBranches().stream())
                .anyMatch(branch -> branch.getName().equals(MASTER_BRANCH) &&
                        branch.getLastCommitSha().equals(EXPECTED_SHA))
        );
    }
}
