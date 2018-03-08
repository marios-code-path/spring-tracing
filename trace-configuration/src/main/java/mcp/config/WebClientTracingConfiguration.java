package mcp.config;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
class WebClientTracingConfiguration {

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(HttpTracing tracing) {
        return restTemplate -> restTemplate
                .setInterceptors(Arrays
                        .asList(TracingClientHttpRequestInterceptor.create(tracing)
                ));
    }
}
