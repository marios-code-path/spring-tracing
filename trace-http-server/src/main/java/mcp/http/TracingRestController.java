package mcp.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Profile("http-web")
@Slf4j
@RestController
public class TracingRestController {
    private final RestTemplate restTemplate;
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TracingGrpcRestController.class);

    public TracingRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/backend")
    public String backend(@RequestHeader("client-id") String clientId) {
        restTemplate.getForObject("http://localhost:8080/delay", String.class);
        return "Greetings " + clientId;
    }

    @GetMapping("/delay")
    public void addDelay() throws Exception{
        long delay = ThreadLocalRandom.current().nextInt(1, 5);
        log.info("Goind to Delay for: " + delay + "seconds");
        TimeUnit.SECONDS.sleep(delay);
        return;
    }

    @GetMapping("/frontend")
    public String frontend() {
        return restTemplate
                .getForObject("http://localhost:8080/backend", String.class);
    }
}