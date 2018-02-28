package mcp.cloudtrace;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
class SampleRestController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SampleRestController.class);

    private final ClientRepository clientRepo;
    private final RestTemplate restTemplate;

    public SampleRestController(ClientRepository ds,
                                RestTemplate rt) {
        this.clientRepo = ds;
        this.restTemplate = rt;
    }

    @GetMapping("/clients")
    public List<Client> deviceNames(HttpServletRequest req) {
        String clientId = req.getHeader("client-id");

        log.info("FYI: client-id = " + clientId);
        return this.clientRepo.findAll();
    }

    @GetMapping("/front")
    public String callClientList() {

        return restTemplate.getForObject("http://localhost:8080/clients", String.class);

    }
}

