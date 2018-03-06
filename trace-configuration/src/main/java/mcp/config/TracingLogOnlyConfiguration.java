package mcp.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.reporter.Reporter;

@Profile("log")
@Configuration
public class TracingLogOnlyConfiguration {

    @Bean
    Tracing tracing() {
        return Tracing.newBuilder()
                .currentTraceContext((MDCCurrentTraceContext.create()))
                .spanReporter(Reporter.CONSOLE)
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }
}
