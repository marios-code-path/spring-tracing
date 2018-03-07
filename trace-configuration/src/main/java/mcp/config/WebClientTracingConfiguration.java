package mcp.config;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("web")
@Configuration
class WebClientTracingConfiguration {

    public WebClientTracingConfiguration(Tracing tracing,
                                         RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder
                .additionalInterceptors
                        (
                            TracingClientHttpRequestInterceptor
                                .create(HttpTracing
                                        .create(tracing)
                                )
                        );
    }
}
