package com.decodetamination.order;

import com.decodetamination.api.order.CreateOrderDto;
import com.decodetamination.api.order.OrderApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public UUID create(@Valid CreateOrderDto request) {
        return orderService.create(orderMapper.map(request));
    }
}
