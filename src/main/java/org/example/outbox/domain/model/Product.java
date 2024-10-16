package org.example.outbox.domain.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private String description;

}
