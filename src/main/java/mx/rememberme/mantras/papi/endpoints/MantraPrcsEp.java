package mx.rememberme.mantras.papi.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MantraPrcsEp {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("mantra")
	public String getMantraFromSapi() {
		return "Gift of holly spirit";
	}
}
