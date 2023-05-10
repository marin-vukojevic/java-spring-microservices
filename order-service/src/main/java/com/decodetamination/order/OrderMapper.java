package com.decodetamination.order;

import com.decodetamination.api.order.CreateOrderDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface OrderMapper {

    Order map(CreateOrderDto request);

    @BeanMapping(ignoreUnmappedSourceProperties = "items")
    OrderCreated map(Order order);
}
