package michal.malek.controller;

import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Hidden;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import michal.malek.model.response.GitHubRepoResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/github")
@Tag(name = "GitHub Controller", description = "Retrieval operations related to GitHub repositories")
public interface GitHubController {

    @GET
    @Path("/repos")
    @Operation(summary = "Retrieve non-fork repositories with branches",
            description = "Fetches repositories of a user, filters out forks, and retrieves their branches.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful retrieval",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GitHubRepoResponse.class))),
            @APIResponse(responseCode = "404", description = "User not found"),
            @APIResponse(responseCode = "500", description = "Unexpected error")
    })
    Uni<Response> getUserRepos(
            @Parameter(description = "GitHub username", required = true) @QueryParam("user") String user,
            @Parameter(description = "Repository type (e.g., all, owned, member)") @QueryParam("type") String type);
}
