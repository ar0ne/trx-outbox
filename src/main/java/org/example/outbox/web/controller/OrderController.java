package org.example.outbox.web.controller;

import org.example.outbox.service.OrderService;
import org.example.outbox.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public List<OrderDto> listOrders() {
        return orderService.findAll();
    }

    @GetMapping(value = "/{id}")
    public OrderDto findOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }
}
