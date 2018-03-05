package mcp;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class TraceGrpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(TraceGrpcApplication.class, args);

    }

}

@Configuration
class TraceGrpcServerConfiguration {

    @Bean
    GrpcTracing grpcTracing(Tracing tracing) {
        return GrpcTracing.create(tracing);
    }
}