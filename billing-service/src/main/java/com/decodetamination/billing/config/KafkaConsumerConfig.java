package com.decodetamination.billing.config;

import com.decodetamination.billing.order.OrderCreated;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${topic.orders.group-id}")
    private String orderGroupId;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderCreated>>
    orderCreatedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreated> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(1000);
        factory.getContainerProperties().setObservationEnabled(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }

    private ConsumerFactory<String, OrderCreated> kafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, orderGroupId);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(OrderCreated.class));
    }
}
