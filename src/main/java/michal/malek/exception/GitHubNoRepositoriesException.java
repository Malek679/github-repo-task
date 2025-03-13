package michal.malek.exception;

import jakarta.ws.rs.core.Response;
import michal.malek.model.message.ErrorMessages;
import org.jboss.resteasy.reactive.ResponseStatus;

public class GitHubNoRepositoriesException extends GitHubException  {
    public GitHubNoRepositoriesException(String user) {
        super(Response.Status.NOT_FOUND.getStatusCode(), String.format(ErrorMessages.USER_HAS_NO_REPOSITORIES, user));
    }
}

