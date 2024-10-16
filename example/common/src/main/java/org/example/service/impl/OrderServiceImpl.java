package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.model.Order;
import org.example.persistence.model.OrderStatus;
import org.example.persistence.model.OutboxEvent;
import org.example.persistence.model.Product;
import org.example.dto.OrderDto;
import org.example.dto.ProductDto;
import org.example.exception.OutboxException;
import org.example.persistence.repository.OrderRepository;
import org.example.persistence.repository.OutboxEventRepository;
import org.example.persistence.repository.ProductRepository;
import org.example.service.OrderService;
import org.example.utils.SerializationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private OutboxEventRepository outboxRepository;

    @Autowired
    private SerializationHelper serializationHelper;

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDto.Mapper::toDto)
                .orElseThrow(() -> new OutboxException("Can not find Order with id " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto findByUuid(String uuid) {
        return orderRepository.findByUuid(uuid)
                .map(OrderDto.Mapper::toDto)
                .orElseThrow(() -> new OutboxException("Can not find Order with uuid " + uuid));
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
        final Set<Long> productIds = dto.products().stream().map(ProductDto::id).collect(Collectors.toSet());
        final Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));
        final Order order = Order.builder()
                .status(OrderStatus.CREATED)
                .products(products)
                .build();
        final Order newOrder = orderRepository.save(order);
        final OutboxEvent outboxEvent = OutboxEvent.builder()
                .type(OutboxEvent.EventType.CREATED)
                .payloadType(newOrder.getClass().getSimpleName())
                .payload(serializationHelper.toJson(newOrder))
                .build();
        outboxRepository.save(outboxEvent);
        return OrderDto.Mapper.toDto(newOrder);
    }
}
