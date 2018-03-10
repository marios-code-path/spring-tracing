package mcp.grpc;

import brave.grpc.GrpcTracing;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"mcp", "mcp.grpc"})
public class GrpcServiceApplication {
    @Bean
    @GRpcGlobalInterceptor
    ServerInterceptor serverInterceptor(GrpcTracing grpcTracing) {
        return grpcTracing.newServerInterceptor();
    }

    public static void main(String[] args) {
        SpringApplication.run(GrpcServiceApplication.class, args);

    }
}
