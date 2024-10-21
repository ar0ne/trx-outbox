package org.example.service;

import org.example.persistence.model.OutboxEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OutboxService {

    Page<OutboxEvent> findAllByOrderByIdAsc(Pageable pageable);

    void deleteAllInBatch(Iterable<OutboxEvent> events);

}
