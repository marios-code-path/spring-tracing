package mcp.cloudtrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class CloudTraceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudTraceApplication.class, args);
	}
}
