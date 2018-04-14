package mcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.amqp.RabbitMQSender;

import java.io.IOException;

@Profile("report-to-rabbit")
@Configuration
public class TracingReportToRabbitConfiguration {
    /**
     * Configuration for sending spans to RabbitMQ
     */
    @Bean
    Sender sender(@Value("${mcp.rabbit.url}") String rabbitmqHostUrl,
                  @Value("${mcp.rabbit.queue}") String zipkinQueue) throws IOException {
        return RabbitMQSender.newBuilder()
                .queue(zipkinQueue)
                .addresses(rabbitmqHostUrl).build();
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    Reporter<Span> spanReporter(Sender sender) {

        return AsyncReporter.create(sender);
    }

}
