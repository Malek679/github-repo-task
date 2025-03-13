package michal.malek.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import michal.malek.model.response.StandardFailureResponse;
import michal.malek.service.GitHubDataService;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;


@Path("/api/github")
@Produces(MediaType.APPLICATION_JSON)
public class GitHubControllerImpl implements GitHubController {

    @Inject
    GitHubDataService dataService;

    @GET
    @Path("/repos")
    @Override
    public Uni<Response> getUserRepos(
            @Parameter(description = "GitHub username", required = true) @QueryParam("user") String user,
            @Parameter(description = "Repository type (e.g., all, owned, member)") @QueryParam("type") String type) {

        type = (type != null) ? type : "owned";
        return dataService.getNonForkReposWithBranches(user, type)
                .onItem().transform(repos -> Response.ok(repos).build())
                .onFailure().recoverWithItem(ex -> Response.status(((WebApplicationException) ex)
                                .getResponse().getStatus()).entity(new StandardFailureResponse(
                                ((WebApplicationException) ex).getResponse().getStatus(), ex.getMessage()))
                        .build());
    }
}

