package org.example.outbox.service.impl;

import org.example.outbox.domain.model.Order;
import org.example.outbox.service.OrderService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class OrderServiceImpl implements OrderService {

    private Set<Order> orders = new HashSet<>();

    @Override
    public Optional<Order> findById(Long id) {
        return this.orders.stream().filter(o -> Objects.equals(o.getId(), id)).findFirst();
    }

    @Override
    public Order save(Order order) {
        Optional<Order> existingOrder = this.findById(order.getId());
        existingOrder.ifPresent(value -> this.orders.remove(value));
        this.orders.add(order);
        return order;
    }
}
