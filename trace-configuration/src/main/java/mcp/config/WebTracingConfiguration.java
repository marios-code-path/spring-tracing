package mcp.config;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Optional;

@Configuration
public class WebTracingConfiguration extends WebMvcConfigurerAdapter {

    private HttpTracing tracing;
    private RestTemplateBuilder restTemplatebuilder;

    public WebTracingConfiguration(HttpTracing tracing,
                                   Optional<RestTemplateBuilder> restTemplateBuilderMaybe) {
        this.tracing = tracing;
        if (restTemplateBuilderMaybe.isPresent()) {
            this.restTemplatebuilder = restTemplateBuilderMaybe.get();
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(TracingHandlerInterceptor.create(tracing));

        if (restTemplatebuilder != null)
            restTemplatebuilder.additionalInterceptors(
                    TracingClientHttpRequestInterceptor.create(tracing)
            );
    }
}
