package com.decodetamination.billing.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventsListener {

    @KafkaListener(topics = "${topic.orders.name}",  containerFactory = "orderCreatedKafkaListenerContainerFactory")
    public void process(OrderCreated event, Acknowledgment acknowledgment) {
        log.info(
                "Reducing balance for customer {} by {} because of order {}",
                event.getCustomerUuid(),
                event.getTotalAmount(),
                event.getUuid().toString());
        acknowledgment.acknowledge();
    }
}
