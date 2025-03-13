package michal.malek.provider;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import michal.malek.model.response.StandardFailureResponse;

@Provider
public class InternalExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new StandardFailureResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                        exception.getMessage()))
                .build();
    }
}
