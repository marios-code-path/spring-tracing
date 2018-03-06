package mcp.cloudtrace;

import mcp.GreetingClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
class TracingRestController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TracingRestController.class);

    private final RestTemplateBuilder restTemplatebuilder;
    private final GreetingClient greetingClient;

    TracingRestController(RestTemplateBuilder builder, GreetingClient greetingClient) {
        this.restTemplatebuilder = builder;
        this.greetingClient = greetingClient;
    }


    @GetMapping("/backend")
    public String backend(HttpServletRequest req) {
        Optional<String> maybeClientId = Optional.ofNullable(req.getHeader("client-id"));
        String clientId = maybeClientId.orElse("");

        log.info("header client-id = " + clientId);
        return greetingClient.greeting(clientId).getHello();
    }

    @GetMapping("/frontend")
    public String frontend() {
        return restTemplatebuilder.build()
                .getForObject("http://localhost:8080/backend", String.class);
    }
}
