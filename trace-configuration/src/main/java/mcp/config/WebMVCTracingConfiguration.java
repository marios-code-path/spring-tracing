package mcp.config;

import brave.http.HttpTracing;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMVCTracingConfiguration extends WebMvcConfigurerAdapter {
    private final HttpTracing httpTracing;

    public WebMVCTracingConfiguration(HttpTracing httpTracing) {
        this.httpTracing = httpTracing;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(TracingHandlerInterceptor.create(httpTracing));
    }


}

