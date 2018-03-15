package mcp.config;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Configuration
class WebClientTracingConfiguration {
    @Bean
    RestTemplate restTemplate(HttpTracing tracing) {
        return new RestTemplateBuilder()
                .additionalInterceptors(TracingClientHttpRequestInterceptor.create(tracing))
                .build();
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate(HttpTracing tracing) {
        return new AsyncRestTemplate().setInterceptors(
                new ArrayList<org.springframework.http.client.AsyncClientHttpRequestInterceptor>(TracingClientHttpRequestInterceptor.create(tracing))
        );
    }
}
