package org.example.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.model.OutboxEvent;
import org.example.service.OutboxService;
import org.example.utils.TransactionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Scheduled(fixedRate = 10_000)
    public void pullOutboxEvents() {
        log.info("Pull outbox event table");
        transactionsHelper.doInTransaction(callback -> {
            List<OutboxEvent> newOutboxEvents = outboxService.findAllByOrderByIdAsc();
            if (newOutboxEvents.isEmpty()) {
                return null;
            }

            // send message to message broker
            newOutboxEvents.forEach(event -> log.info("Event: {}", event.getId()));

            outboxService.deleteAllInBatch(newOutboxEvents);
            return null;
        });

    }

}
