package org.example.api.producer;

import lombok.extern.slf4j.Slf4j;
import org.example.persistence.model.event.OrderCreated;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreated event) {
        log.info("After commit");
        log.info("Order id {}", event.id());

        // send message to message broken ?
    }

}
