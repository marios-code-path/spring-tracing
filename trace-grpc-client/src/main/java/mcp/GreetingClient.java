package mcp;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class GreetingClient {

    private final ManagedChannel managedChannel;

    public GreetingClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
    }

    @PostConstruct
    private void initializeClient() {
        greetingServiceBlockingStub = GreetingServiceGrpc.newBlockingStub(managedChannel);
    }

    private GreetingServiceGrpc.GreetingServiceBlockingStub
            greetingServiceBlockingStub;

    public Greeting greeting(String name) {

        Greet greeting = Greet
                .newBuilder()
                .setName(name)
                .build();

        return greetingServiceBlockingStub.greeting(greeting);
    }
}
