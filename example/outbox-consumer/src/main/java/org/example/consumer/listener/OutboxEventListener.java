package org.example.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.shaded.com.google.protobuf.Message;
import org.example.persistence.model.OutboxEvent;
import org.example.service.OutboxService;
import org.example.utils.TransactionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class OutboxEventListener {

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private TransactionsHelper transactionsHelper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(initialDelay = 5_000, fixedRate = 10_000)
    public void pullOutboxEvents() {
        log.info("Pull outbox event table");
        transactionsHelper.doInTransaction(callback -> {
            List<OutboxEvent> newOutboxEvents = outboxService.findAllByOrderByIdAsc();
            if (newOutboxEvents.isEmpty()) {
                return null;
            }

            // send message to message broker
            newOutboxEvents.forEach(event -> {
                log.info("Send event to message broker: {}", event.getId());
                kafkaTemplate.send("messages", event.getPayload());
            });

            outboxService.deleteAllInBatch(newOutboxEvents);
            return null;
        });

    }

}
