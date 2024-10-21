package org.example.kafka.event;

import lombok.AllArgsConstructor;
import org.example.event.EventPublisher;
import org.example.properties.OutboxAppProperties;
import org.springframework.kafka.core.KafkaTemplate;

@AllArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final OutboxAppProperties properties;

    @Override
    public void send(String payload) {
        kafkaTemplate.send(properties.getKafkaDefaults().getTopicName(), payload);
    }
}
