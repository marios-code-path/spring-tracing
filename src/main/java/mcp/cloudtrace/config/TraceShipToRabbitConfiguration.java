package mcp.cloudtrace.config;

import mcp.cloudtrace.config.TracePropagationConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.Sender;
import zipkin2.reporter.amqp.RabbitMQSender;

@Configuration
public class TraceShipToRabbitConfiguration extends TracePropagationConfiguration {
    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String rabbitmqHostUrl) {
        RabbitMQSender sender;

        sender = RabbitMQSender.newBuilder()
                .queue("zipkin")
                .addresses("localhost:5672").build();

        return sender;
    }
}
