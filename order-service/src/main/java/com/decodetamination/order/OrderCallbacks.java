package com.decodetamination.order;

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderCallbacks implements BeforeConvertCallback<Order> {

    @Override
    public Order onBeforeConvert(Order order) {
        order.setUuid(UUID.randomUUID());
        return order;
    }
}