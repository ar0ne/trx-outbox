package org.example.warehouse.config;

import lombok.RequiredArgsConstructor;
import org.example.constants.DefaultKafkaProperties;
import org.example.properties.OutboxAppProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;


@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    private final OutboxAppProperties appProperties;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(appProperties.getKafkaDefaults().getTopicPartitionsNumber());
        factory.getContainerProperties().setPollTimeout(appProperties.getKafkaDefaults().getPollTimeout());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(DefaultKafkaProperties.getConsumerProperties(kafkaProperties, "outbox"));
    }

}
