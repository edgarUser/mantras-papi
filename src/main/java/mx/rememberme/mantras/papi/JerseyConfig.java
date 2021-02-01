package mx.rememberme.mantras.papi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import mx.rememberme.mantras.papi.endpoints.MantraEndpoint;
import mx.rememberme.mantras.papi.exceptions.InternalErrorMapper;
import mx.rememberme.mantras.papi.exceptions.MantraNotFoundMapper;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(MantraEndpoint.class);
		register(InternalErrorMapper.class);
		register(MantraNotFoundMapper.class);
	}
}
