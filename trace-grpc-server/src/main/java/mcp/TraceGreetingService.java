package mcp;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class TraceGreetingService extends GreetingServiceGrpc.GreetingServiceImplBase {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TraceGreetingService.class);

    @Override
    public void sayHi(Empty request, StreamObserver<Greeting> responseObserver) {
        log.info("sayHi");
        responseObserver.onNext(
                Greeting
                        .newBuilder()
                        .setHello("hello")
                        .build());
        responseObserver.onCompleted();
    }

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
