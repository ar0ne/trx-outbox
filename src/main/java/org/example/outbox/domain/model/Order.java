package org.example.outbox.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Order {

    private Long id;

    private LocalDateTime created;

    private OrderStatus status;

    private Set<Product> products = new HashSet<>();

}
