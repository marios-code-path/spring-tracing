package mcp.cloudtrace;

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
    Tracing logTracing() {
        return Tracing.newBuilder()
                .currentTraceContext((MDCCurrentTraceContext.create()))
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    @Configuration
    class WebTracingConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(TracingHandlerInterceptor.create(
                    HttpTracing.create(Tracing
                            .newBuilder()
                            .currentTraceContext(MDCCurrentTraceContext.create())
                            .build())));
        }
    }
}
