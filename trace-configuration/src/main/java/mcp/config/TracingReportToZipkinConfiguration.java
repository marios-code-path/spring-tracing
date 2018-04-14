package mcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Profile("zipkin")
@Configuration
class TracingReportToZipkinConfiguration {

    /**
     * Configuration for how to send spans to directly to Zipkin
     */
    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }


    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    Reporter<Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }
}
