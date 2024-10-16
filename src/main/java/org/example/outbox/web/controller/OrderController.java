package org.example.outbox.web.controller;

import org.example.outbox.domain.model.Order;
import org.example.outbox.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/{id}")
    public Order listOrder(@PathVariable Long id) {
        return orderService.findById(id).orElse(null);
    }
}
