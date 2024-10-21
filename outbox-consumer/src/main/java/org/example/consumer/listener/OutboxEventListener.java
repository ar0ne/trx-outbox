package org.example.consumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.EventPublisher;
import org.example.persistence.model.OutboxEvent;
import org.example.properties.OutboxAppProperties;
import org.example.service.OutboxService;
import org.example.utils.TransactionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class OutboxEventListener {

    private final OutboxAppProperties outboxAppProperties;

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private TransactionsHelper transactionsHelper;

    @Autowired
    private EventPublisher eventPublisher;

    @Scheduled(initialDelay = 5_000, fixedRateString = "${outbox.pull-outbox-events-rate}")
    public void pullOutboxEvents() {
        log.info("Pull outbox event table");
        transactionsHelper.doInTransaction(callback -> {
            List<OutboxEvent> newOutboxEvents = outboxService.findAllByOrderByIdAsc(Pageable.ofSize(outboxAppProperties.getBatchSize())).toList();
            if (newOutboxEvents.isEmpty()) {
                return null;
            }

            // send message to message broker
            newOutboxEvents.forEach(event -> {
                log.info("Send event to message broker: {}", event.getId());
                eventPublisher.send(event.getPayload());
            });

            outboxService.deleteAllInBatch(newOutboxEvents);
            return null;
        });

    }

}
