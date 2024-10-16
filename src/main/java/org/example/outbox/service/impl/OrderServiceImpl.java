package org.example.outbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.outbox.domain.model.Order;
import org.example.outbox.dto.OrderDto;
import org.example.outbox.exception.OutboxException;
import org.example.outbox.persistence.repository.OrderRepository;
import org.example.outbox.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

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
        final Order order = OrderDto.Mapper.toModel(dto);
        return OrderDto.Mapper.toDto(orderRepository.save(order));
    }
}
