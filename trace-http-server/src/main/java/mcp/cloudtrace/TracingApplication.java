package mcp.cloudtrace;

import mcp.GreetingClient;
import mcp.config.TracingConfiguration;
import mcp.config.WebTracingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackageClasses = {TracingConfiguration.class,
        GreetingClient.class})
public class TracingApplication {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Bean
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(TracingApplication.class, args);
    }
}
