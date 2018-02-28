package mcp.cloudtrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@SpringBootApplication
public class CloudTraceApplication {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // SETUP SOME DATA! - this is an example afterall
    @EventListener
    public void handleContextBeforeRefresh(ContextRefreshedEvent event) {
        event.getApplicationContext().getBean(ClientRepository.class)
                .save(Arrays.asList(
                   new Client(null, "IOS"),
                   new Client(null, "ANDROID"),
                   new Client(null, "WEB")
                ));
    }
    public static void main(String[] args) {
        SpringApplication.run(CloudTraceApplication.class, args);
    }
}
