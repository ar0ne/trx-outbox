package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.persistence.model.Order;
import org.example.persistence.model.OrderStatus;
import static org.example.constants.CommonConstants.ISO_DATE_TIME_PATTERN;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public record OrderDto ( // @formatter:off
    Long id,

    String uuid,

    @JsonFormat(pattern = ISO_DATE_TIME_PATTERN)
    LocalDateTime createdAt,

    @JsonFormat(pattern = ISO_DATE_TIME_PATTERN)
    LocalDateTime updatedAt,

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
            return new OrderDto(order.getId(), order.getUuid(), order.getCreatedAt(), order.getUpdatedAt(), order.getStatus(), productsDto);
        }

        public static Order toModel(OrderDto dto) {
            if (isNull(dto)) {
                return null;
            }

            return Order.builder()
                    .id(dto.id())
                    .uuid(dto.uuid())
                    .createdAt(dto.createdAt())
                    .updatedAt(dto.updatedAt())
                    .status(dto.status())
                    .build();
        }
    }
}
