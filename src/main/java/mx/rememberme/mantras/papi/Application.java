package mx.rememberme.mantras.papi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.rememberme.mantras.papi.services.MantraService;
import mx.rememberme.mantras.papi.services.MantraServiceImpl;

@Configuration
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MantraService getMantraService() {
		return new MantraServiceImpl();
	}

}
