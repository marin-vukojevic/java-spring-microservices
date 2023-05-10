package com.decodetamination.order;

import com.decodetamination.order.items.ItemClient;
import com.decodetamination.order.items.ItemServiceUnavailable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value(value = "${topic.orders.name}")
    private String orderEventsTopic;

    private final OrderDao orderDao;
    private final ItemClient itemClient;
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Transactional
    public UUID create(Order order) {
        setTotalAmount(order);
        orderDao.save(order);
        sendEvent(order);
        log.info("Created order {}.", order.getUuid());
        return order.getUuid();
    }

    private void setTotalAmount(Order order) {
        // inefficient implementation, for demo purpose only
        order.setTotalAmount(
                order.getItems().stream()
                        .map(it -> it.getCount() * getItemPrice(it.getUuid()))
                        .reduce(Integer::sum)
                        .orElse(0));
    }

    private int getItemPrice(UUID itemUuid) {
        return circuitBreakerFactory.create("getItemPrice")
                .run(() -> itemClient.get(itemUuid).price(), throwable -> {
                    throw new ItemServiceUnavailable();
                });
    }

    private void sendEvent(Order order) {
        kafkaTemplate.send(
                orderEventsTopic,
                OrderCreated.builder()
                        .uuid(order.getUuid())
                        .customerUuid(order.getCustomerUuid())
                        .totalAmount(order.getTotalAmount())
                        .build());
    }
}
