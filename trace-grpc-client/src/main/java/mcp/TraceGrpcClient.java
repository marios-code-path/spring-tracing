package mcp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TraceGrpcClient {

    @PostConstruct
    private void initializeClient() {
        greetingServiceBlockingStub = GreetingServiceGrpc.newBlockingStub(managedChannel());
    }

    @Bean
    private ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext(true)
                .build();
    }

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
