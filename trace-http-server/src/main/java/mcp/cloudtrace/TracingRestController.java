package mcp.cloudtrace;

import mcp.TraceGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
class TracingRestController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TracingRestController.class);
    private final RestTemplate restTemplate;

    public TracingRestController(RestTemplate rt) {
        this.restTemplate = rt;
    }

    @Autowired
    TraceGrpcClient traceGrpcClient;

    @GetMapping("/backend")
    public String backend(HttpServletRequest req) {
        String clientId = req.getHeader("client-id");
        log.info("clientId=" + clientId);

        return traceGrpcClient.greeting(clientId).getHello();
    }

    @GetMapping("/frontend")
    public String frontend() {
        return restTemplate.getForObject("http://localhost:8080/backend", String.class);
    }
}
