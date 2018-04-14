package mcp.http;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Profile("http-web")
@RestController
public class TracingRestController {
    private final RestTemplate restTemplate;
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TracingGrpcRestController.class);

    public TracingRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/backend")
    public String backend() {
        return "Greetings";
    }

    @GetMapping("/frontend")
    public String frontend() {
        return restTemplate
                .getForObject("http://localhost:8080/backend", String.class);
    }
}