package mx.rememberme.mantras.papi.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InternalErrorMapper implements ExceptionMapper<InternalErrorException> {

	@Override
	public Response toResponse(InternalErrorException exception) {
		return Response.status(500).entity(exception.getMessage()).type("text/plain").build();
	}

}
