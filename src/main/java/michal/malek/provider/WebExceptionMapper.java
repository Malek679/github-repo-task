package michal.malek.provider;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import michal.malek.model.response.StandardFailureResponse;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(new StandardFailureResponse(exception.getResponse().getStatus(), exception.getMessage()))
                .build();
    }

}
