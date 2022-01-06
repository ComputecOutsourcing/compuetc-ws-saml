package co.experian.computec.sts.jaxrs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class SAMLExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception exception) {
        return Response.status(500).entity("Error en su petición").type("text/plain").build();

    }

}