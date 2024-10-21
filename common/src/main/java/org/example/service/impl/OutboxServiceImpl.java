package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.model.OutboxEvent;
import org.example.persistence.repository.OutboxEventRepository;
import org.example.service.OutboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OutboxEvent> findAllByOrderByIdAsc() {
        return outboxEventRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    @Override
    public void deleteAllInBatch(Iterable<OutboxEvent> events) {
        outboxEventRepository.deleteAllInBatch(events);
    }
}
