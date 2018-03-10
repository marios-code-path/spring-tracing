package mcp.grpc;

import io.grpc.stub.StreamObserver;
import mcp.Empty;
import mcp.Greet;
import mcp.Greeting;
import mcp.GreetingServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GrpcService extends GreetingServiceGrpc.GreetingServiceImplBase {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GrpcService.class);

    @Override
    public void greeting(Greet request, StreamObserver<Greeting> responseObserver) {
        log.info("Greetings, " + request.getName());
        responseObserver.onNext(
                Greeting
                        .newBuilder()
                        .setHello("hello " + request.getName())
                        .build());
        responseObserver.onCompleted();
    }
}
