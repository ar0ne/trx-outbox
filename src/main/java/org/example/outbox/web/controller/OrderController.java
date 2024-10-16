package org.example.outbox.web.controller;

import org.example.outbox.domain.model.Order;
import org.example.outbox.service.OrderService;
import org.example.outbox.web.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public List<OrderDto> listOrders() {
        return orderService.findAll().stream().map(OrderDto.Mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public OrderDto findOrder(@PathVariable Long id) {
        return orderService.findById(id).map(OrderDto.Mapper::toDto).orElse(null);
    }
}
