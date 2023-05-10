package com.decodetamination.api.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(@NotNull UUID customerUuid, @NotEmpty List<OrderItemDto> items) {
}
