package mcp.config;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TracingGrpcConfiguration {

    @Bean
    public GrpcTracing grpcTracing(Tracing tracing) {

        return GrpcTracing.create(tracing);
    }

    @Bean
    public ServerInterceptor grpcServerInterceptor(GrpcTracing grpcTracing) {
        return grpcTracing.newServerInterceptor();
    }

    @Bean
    public ManagedChannelBuilder managedChannelBuilder(GrpcTracing grpcTracing) {
        return ManagedChannelBuilder.forAddress("localhost", 6565 )
                .intercept(grpcTracing.newClientInterceptor())
                .usePlaintext(true);
    }

    @Bean
    public ManagedChannel managedChannel(ManagedChannelBuilder channelBuilder) {
        return channelBuilder
                .build();
    }
}
