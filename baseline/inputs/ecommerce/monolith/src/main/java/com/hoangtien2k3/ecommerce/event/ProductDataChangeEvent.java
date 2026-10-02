package com.hoangtien2k3.ecommerce.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductDataChangeEvent extends ApplicationEvent {
    private final Long productId;
    private final Operation operation;

    public ProductDataChangeEvent(Object source, Long productId, Operation operation) {
        super(source);
        this.productId = productId;
        this.operation = operation;
    }

    public enum Operation {
        CREATE, UPDATE, DELETE
    }
}
