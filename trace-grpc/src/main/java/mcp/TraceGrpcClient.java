package mcp;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TraceGrpcClient {

    @PostConstruct
    private void initializeClient() {
        greetingServiceBlockingStub = GreetingServiceGrpc.newBlockingStub(managedChannel);
    }

    @Autowired
    ManagedChannel managedChannel;

    Logger log = LoggerFactory.getLogger(TraceGrpcClient.class);

    private GreetingServiceGrpc.GreetingServiceBlockingStub
            greetingServiceBlockingStub;

    public Greeting hi() {
        log.info("GRPC-HI");
        return greetingServiceBlockingStub.sayHi(Empty.newBuilder().build());
    }

    public Greeting greeting(String name) {

        log.info("GRPC-GREETING");
        Greet greeting = Greet
                .newBuilder()
                .setName(name)
                .build();

        return greetingServiceBlockingStub.greeting(greeting);
    }
}
