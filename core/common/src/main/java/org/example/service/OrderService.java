package org.example.service;

import org.example.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    OrderDto save(OrderDto order);

}
