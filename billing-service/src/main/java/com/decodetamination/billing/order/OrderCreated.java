package com.decodetamination.billing.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderCreated {

    private UUID uuid;
    private UUID customerUuid;
    private Integer totalAmount;
}
