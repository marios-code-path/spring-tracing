package mcp.http;

import mcp.GreetingClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Profile("grpc-web")
@RestController
public class TracingGrpcRestController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TracingGrpcRestController.class);

    private final GreetingClient greetingClient;
    private final RestTemplate restTemplate;

    public TracingGrpcRestController(GreetingClient greetingClient,
                                     RestTemplate restTemplate) {
        this.greetingClient = greetingClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/delay")
    public void addDelay() throws Exception{
        long delay = ThreadLocalRandom.current().nextInt(1, 5);
        log.info("delay for: " + delay + "seconds");
        TimeUnit.SECONDS.sleep(delay);
        return;
    }

    @GetMapping("/backend")
    public String backend(HttpServletRequest req) {
        String clientId = Optional
                .ofNullable(req.getHeader("client-id"))
                .orElse("none");
        
        return greetingClient.greeting(clientId).getHello();
    }

    @GetMapping("/frontend")
    public String frontend()
    {
        return restTemplate
                .getForObject("http://localhost:8080/backend", String.class);
    }
}
