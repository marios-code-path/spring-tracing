package mcp.grpc;

import io.grpc.stub.StreamObserver;
import mcp.Greet;
import mcp.Greeting;
import mcp.GreetingServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.web.client.RestTemplate;

@GRpcService(applyGlobalInterceptors = true)
public class GrpcService extends GreetingServiceGrpc.GreetingServiceImplBase {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GrpcService.class);

    private final RestTemplate restTemplate;

    public GrpcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void greeting(Greet request, StreamObserver<Greeting> responseObserver) {
        log.info("Greetings, " + request.getName());
        responseObserver.onNext(
                Greeting
                        .newBuilder()
                        .setHello("Hello " + request.getName())
                        .build());

        restTemplate.getForObject("http://localhost:8080/delay", String.class);

        responseObserver.onCompleted();
    }
}
