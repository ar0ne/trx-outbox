package org.example.outbox.service;

import org.example.outbox.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    OrderDto save(OrderDto order);

}
