package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.model.OutboxEvent;
import org.example.persistence.repository.OutboxEventRepository;
import org.example.service.OutboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<OutboxEvent> findAllByOrderByIdAsc(Pageable pageable) {
        return outboxEventRepository.findAllByOrderByIdAsc(pageable);
    }

    @Transactional
    @Override
    public void deleteAllInBatch(Iterable<OutboxEvent> events) {
        outboxEventRepository.deleteAllInBatch(events);
    }
}
