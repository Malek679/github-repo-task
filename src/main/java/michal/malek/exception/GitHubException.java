package michal.malek.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import michal.malek.model.response.StandardFailureResponse;

@Getter
public class GitHubException extends WebApplicationException {
    private final int code;

    public GitHubException(int code, String message) {
        super(message, Response.status(code).entity(new StandardFailureResponse(code, message)).build());
        this.code = code;
    }
}


