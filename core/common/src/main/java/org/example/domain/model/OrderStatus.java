package org.example.domain.model;

import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED(0),
    IN_PROGRESS(1),
    COMPLETED(2),
    LOST(3)
    ;

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

}
