package mcp.config;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Profile("web")
@Configuration
public class WebTracingConfiguration extends WebMvcConfigurerAdapter {
    private HttpTracing tracing;

    public WebTracingConfiguration(HttpTracing tracing) {
        this.tracing = tracing;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(TracingHandlerInterceptor.create(tracing));
    }
}
