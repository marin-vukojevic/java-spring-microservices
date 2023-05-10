package com.decodetamination.api.order;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface OrderApi {

    @PostMapping("orders")
    UUID create(@Valid @RequestBody CreateOrderDto request);
}
