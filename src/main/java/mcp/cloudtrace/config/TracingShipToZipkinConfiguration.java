package mcp.cloudtrace.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.sampler.Sampler;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import mcp.cloudtrace.config.TracePropagationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * This adds tracing configuration to any web mvc controllers or rest template clients. This should
 * be configured last.
 */
//@Configuration
class TracingShipToZipkinConfiguration {
    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }


    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    AsyncReporter<Span> spanReporter(Sender sender) {

        return AsyncReporter.create(sender);
    }

    @Bean
    RestTemplate restTemplate(HttpTracing tracing) {
        return new RestTemplateBuilder()
                .interceptors(TracingClientHttpRequestInterceptor.create(tracing))
                .build();
    }

    @Bean
    Tracing tracing(@Value("${mcp:spring-tracing}") String serviceName,
                    AsyncReporter<Span> spanReporter) {
        return Tracing
                .newBuilder()
                .sampler(Sampler.ALWAYS_SAMPLE)
                .localServiceName(serviceName)
                .propagationFactory(ExtraFieldPropagation
                        .newFactory(B3Propagation.FACTORY, "client-id"))
                .currentTraceContext(MDCCurrentTraceContext.create())
                .spanReporter(spanReporter)
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
