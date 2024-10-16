package org.example.consumer.dto;

import org.example.consumer.domain.model.Product;

import static java.util.Objects.isNull;

public record ProductDto (Long id, String name, String description) {

    public static class Mapper {
        public static Product toModel(ProductDto dto) {
            if (isNull(dto)) {
                return null;
            }
            return Product.builder()
                    .id(dto.id())
                    .name(dto.name())
                    .description(dto.description())
                    .build();
        }

        public static ProductDto toDto(Product product) {
            if (isNull(product)) {
                return null;
            }
            return new ProductDto(product.getId(), product.getName(), product.getDescription());
        }
    }

}
