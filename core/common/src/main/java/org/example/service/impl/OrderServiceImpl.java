package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.example.domain.model.Product;
import org.example.dto.OrderDto;
import org.example.dto.ProductDto;
import org.example.exception.OutboxException;
import org.example.persistence.repository.OrderRepository;
import org.example.persistence.repository.ProductRepository;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDto.Mapper::toDto)
                .orElseThrow(() -> new OutboxException("Can not find Order with id " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderDto.Mapper::toDto).toList();
    }

    @Transactional
    @Override
    public OrderDto save(OrderDto dto) {
        Set<Long> productIds = dto.products().stream().map(ProductDto::id).collect(Collectors.toSet());
        Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));
        final Order order = Order.builder()
                .id(null)
                .status(OrderStatus.CREATED)
                .created(LocalDateTime.now())
                .products(products)
                .build();
        return OrderDto.Mapper.toDto(orderRepository.save(order));
    }
}
