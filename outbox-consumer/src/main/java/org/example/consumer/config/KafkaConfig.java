package org.example.consumer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.constants.DefaultKafkaProperties;
import org.example.kafka.event.KafkaEventPublisher;
import org.example.properties.OutboxAppProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    private final OutboxAppProperties appProperties;

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(appProperties.getKafkaDefaults().getTopicName())
                .partitions(appProperties.getKafkaDefaults().getTopicPartitionsNumber())
                .replicas(appProperties.getKafkaDefaults().getTopicReplicationFactor())
                .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> properties = DefaultKafkaProperties.getProducerProperties(kafkaProperties, appProperties.getKafkaDefaults().getLingerMs());
        DefaultKafkaProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(properties);
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaEventPublisher eventPublisher() {
        return new KafkaEventPublisher(kafkaTemplate(), appProperties);
    }
}
