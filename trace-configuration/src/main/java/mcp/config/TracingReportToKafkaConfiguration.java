package mcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;

import java.io.IOException;

@Profile("report-to-kafka")
@Configuration
public class TracingReportToKafkaConfiguration {

    @Bean
    Sender sender(@Value("${mcp.kafka.url}") String kafkaUrl) throws IOException {
        return KafkaSender.create(kafkaUrl);
    }

    @Bean
    Reporter<Span> spanReporter(Sender sender) {

        return AsyncReporter.create(sender);
    }

}
