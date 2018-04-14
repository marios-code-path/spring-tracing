package mcp.config;

import brave.http.HttpTracing;
import brave.spring.web.TracingAsyncClientHttpRequestInterceptor;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

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
        AsyncRestTemplate async = new AsyncRestTemplate();

        async.setInterceptors(Arrays.asList(
                TracingAsyncClientHttpRequestInterceptor.create(tracing)
        ));

        return async;
    }
}
