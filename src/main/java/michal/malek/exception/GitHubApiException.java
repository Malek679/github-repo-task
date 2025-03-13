package michal.malek.exception;

import jakarta.ws.rs.core.Response;

public class GitHubApiException extends GitHubException {
    public GitHubApiException(String message) {
        super(Response.Status.BAD_GATEWAY.getStatusCode(), message);
    }
}