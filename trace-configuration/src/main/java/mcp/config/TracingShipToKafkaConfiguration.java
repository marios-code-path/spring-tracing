package mcp.config;

import brave.Tracing;
import brave.http.HttpTracing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;

import java.io.IOException;

@Profile({"kafka"})
@Configuration
public class TracingShipToKafkaConfiguration {
    /**
     * Configuration for sending spans to Kafka
     */
    @Bean
    Sender sender(@Value("${mcp.kafka.url}") String kafkaUrl) throws IOException {
        return KafkaSender.create(kafkaUrl);
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    AsyncReporter<Span> spanReporter(Sender sender) {

        return AsyncReporter.create(sender);
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }
}
