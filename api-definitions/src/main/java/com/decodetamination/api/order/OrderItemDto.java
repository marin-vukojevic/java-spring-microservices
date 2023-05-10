package com.decodetamination.api.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemDto(@NotNull UUID uuid, @Min(1) int count) {
}
