package org.example.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewOrderDto ( // @formatter:off
        @NotNull
        List<Long> products
    ) { // @formatter: on
}
