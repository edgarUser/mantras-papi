package mx.rememberme.mantras.papi.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MantraNotFoundMapper implements ExceptionMapper<MantraNotFoundException> {

	@Override
	public Response toResponse(MantraNotFoundException exception) {
		return Response.status(404).entity(exception.getMessage()).type("text/plain").build();
	}

}
