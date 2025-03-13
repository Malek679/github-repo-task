package michal.malek.exception;

import jakarta.ws.rs.core.Response;
import michal.malek.model.message.ErrorMessages;

public class GitHubUserNotFoundException extends GitHubException {
    public GitHubUserNotFoundException(String user) {
        super(Response.Status.NOT_FOUND.getStatusCode(), String.format(ErrorMessages.USER_NOT_FOUND, user));
    }
}

