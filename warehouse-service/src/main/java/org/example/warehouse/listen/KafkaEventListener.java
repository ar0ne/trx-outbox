package org.example.warehouse.listen;

import lombok.extern.slf4j.Slf4j;
import org.example.event.EventListener;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "warehouseListener", topics = "${outbox.kafka-defaults.topic-name}")
public class KafkaEventListener implements EventListener {

    @KafkaHandler
    @Override
    public void listen(String payload) {
        log.info("Received message: {}", payload);

        // TODO: read "warehouse" database and publish another event, e.g. can warehouse fulfill the order?
    }
}
