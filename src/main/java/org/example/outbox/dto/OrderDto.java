package org.example.outbox.dto;

import org.example.outbox.domain.model.Order;
import org.example.outbox.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public record OrderDto ( // @formatter:off
    Long id,

    String uuid,

    LocalDateTime created,

    OrderStatus status,

    Set<ProductDto> products
) { // @formatter:on

    public static class Mapper {
        public static OrderDto toDto(Order order) {
            if (isNull(order)) {
                return null;
            }
            Set<ProductDto> productsDto = order.getProducts().stream()
                    .map(ProductDto.Mapper::toDto)
                    .collect(Collectors.toSet());
            return new OrderDto(order.getId(), order.getUuid(), order.getCreated(), order.getStatus(), productsDto);
        }

        public static Order toModel(OrderDto dto) {
            if (isNull(dto)) {
                return null;
            }

            return Order.builder()
                    .id(dto.id())
                    .uuid(dto.uuid())
                    .created(dto.created())
                    .status(dto.status())
                    .build();
        }
    }
}
