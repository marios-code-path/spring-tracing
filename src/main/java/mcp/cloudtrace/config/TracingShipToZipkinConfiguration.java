package mcp.cloudtrace.config;

import mcp.cloudtrace.config.TracePropagationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * This adds tracing configuration to any web mvc controllers or rest template clients. This should
 * be configured last.
 */
//@Configuration
class TracingShipToZipkinConfiguration extends TracePropagationConfiguration {
    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }

}
