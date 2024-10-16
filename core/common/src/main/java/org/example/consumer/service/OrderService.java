package org.example.consumer.service;

import org.example.consumer.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    OrderDto save(OrderDto order);

}
