package org.example.consumer.web.controller;

import jakarta.validation.Valid;
import org.example.consumer.domain.model.OrderStatus;
import org.example.consumer.dto.NewOrderDto;
import org.example.consumer.dto.ProductDto;
import org.example.consumer.service.OrderService;
import org.example.consumer.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PostMapping
    public OrderDto createOrder(@RequestBody @Valid NewOrderDto newOrderDto) {
        final Set<ProductDto> productsDto = newOrderDto.products().stream()
                .map(id -> new ProductDto(id, null, null))
                .collect(Collectors.toSet());
        final OrderDto dto = new OrderDto(null, null, null, OrderStatus.CREATED, productsDto);
        return orderService.save(dto);
    }
}
