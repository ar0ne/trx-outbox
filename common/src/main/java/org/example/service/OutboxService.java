package org.example.service;

import org.example.persistence.model.OutboxEvent;

import java.util.List;

public interface OutboxService {

    List<OutboxEvent> findAllByOrderByIdAsc();

    void deleteAllInBatch(Iterable<OutboxEvent> events);

}
