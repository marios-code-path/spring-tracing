package mcp.grpc;

import mcp.config.TracingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackageClasses = {TraceGreetingService.class, TracingConfiguration.class})
public class TraceGrpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(TraceGrpcApplication.class, args);

    }
}
