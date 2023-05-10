package com.decodetamination.order;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderCreated {

    private UUID uuid;
    private UUID customerUuid;
    private Integer totalAmount;
}
