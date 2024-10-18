package org.example.warehouse.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {

    @KafkaListener(id = "listen1", topics = "messages")
    public void listen1(Object in) {
        log.warn("Received message: {}", in);
    }

}