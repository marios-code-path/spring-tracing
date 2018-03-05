package mcp.config;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;
import java.io.IOException;

@Profile("kafka")
@Configuration
public class TracingShipToKafkaConfiguration {
    /**
     * Configuration for sending spans to RabbitMQ
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
}
