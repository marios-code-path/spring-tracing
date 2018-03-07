package mcp.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.reporter.Reporter;

@Profile("log")
@Configuration
public class TracingLogOnlyConfiguration {

    @Bean
    Tracing tracing(@Value("${mcp:spring-tracing}") String serviceName) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                .currentTraceContext((MDCCurrentTraceContext.create()))
                .spanReporter(Reporter.CONSOLE)
                .build();
    }
}
