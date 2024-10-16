package org.example.outbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.outbox.domain.model.Order;
import org.example.outbox.persistence.repository.OrderRepository;
import org.example.outbox.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        // TODO: DTO
        return orderRepository.save(order);
    }
}
