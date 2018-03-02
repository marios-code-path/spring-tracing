package mcp.cloudtrace.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class TraceServiceOnlyConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Tracing simpleTracing() {
        return Tracing.newBuilder()
                .currentTraceContext((MDCCurrentTraceContext.create()))
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    @Configuration
    public static class WebTracingConfiguration extends WebMvcConfigurerAdapter {

        private final HttpTracing tracing;

        public WebTracingConfiguration(HttpTracing tracing) {
            this.tracing = tracing;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(TracingHandlerInterceptor.create(tracing));
        }
    }
}
