package mcp.http;

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
        String clientId = Optional
                .ofNullable(req.getHeader("client-id")).orElse("");

        log.info("header client-id = " + clientId);
        return "Greetings, " + clientId;
    }

    @GetMapping("/frontend")
    public String frontend() {
        return restTemplatebuilder.build()
                .getForObject("http://localhost:8080/backend", String.class);
    }

    @GetMapping("/backend-grpc")
    public String backendGrpc(HttpServletRequest req) {
        Optional<String> maybeClientId = Optional.ofNullable(req.getHeader("client-id"));
        String clientId = maybeClientId.orElse("");

        log.info("header client-id = " + clientId);
        return greetingClient.greeting(clientId).getHello();
    }

    @GetMapping("/frontend-grpc")
    public String frontendGrpc() {
        return restTemplatebuilder.build()
                .getForObject("http://localhost:8080/backend-grpc", String.class);
    }
}
