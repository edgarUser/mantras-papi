package mx.rememberme.mantras.papi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import mx.rememberme.mantras.papi.endpoints.MantraPrcsEp;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(MantraPrcsEp.class);
	}
}
