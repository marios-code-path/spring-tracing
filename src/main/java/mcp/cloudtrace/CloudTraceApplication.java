package mcp.cloudtrace;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class CloudTraceApplication {


	@Bean
	CommandLineRunner run(ClientRepository repository) {
		return args -> {
			List<Client> clients = Arrays.asList(
					new Client(null, "IOS"),
					new Client(null, "ANDROID"),
					new Client(null, "WEB"));
			clients.forEach(repository::save);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudTraceApplication.class, args);
	}
}
