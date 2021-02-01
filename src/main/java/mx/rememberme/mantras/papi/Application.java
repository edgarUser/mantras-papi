package mx.rememberme.mantras.papi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.rememberme.mantras.papi.adapters.MantraAdapter;
import mx.rememberme.mantras.papi.exceptions.InternalErrorMapper;
import mx.rememberme.mantras.papi.exceptions.MantraNotFoundMapper;
import mx.rememberme.mantras.papi.services.MantraService;
import mx.rememberme.mantras.papi.services.MantraServiceImpl;

@Configuration
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public MantraAdapter getMantraAdapter() {
		return new MantraAdapter();
	}

	@Bean
	public MantraService getMantraService() {
		return new MantraServiceImpl(getMantraAdapter());
	}

	@Bean
	public InternalErrorMapper getInternalErrorMapper() {
		return new InternalErrorMapper();
	}

	@Bean
	public MantraNotFoundMapper getMantraExceptionMapper() {
		return new MantraNotFoundMapper();
	}

}
