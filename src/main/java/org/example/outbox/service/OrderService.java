package org.example.outbox.service;

import org.example.outbox.domain.model.Order;

import java.util.Optional;

public interface OrderService {

    Optional<Order> findById(Long id);

    Order save(Order order);

}
