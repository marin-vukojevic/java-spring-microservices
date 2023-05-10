package com.decodetamination.order;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Table("orders")
public class Order {

    @Id
    private UUID uuid;
    private UUID customerUuid;
    @MappedCollection(idColumn = "order_uuid")
    private Set<OrderItem> items;
    private Integer totalAmount;

    @Data
    @Builder
    @Table("order_items")
    public static class OrderItem {

        @Column("item_uuid")
        private UUID uuid;
        @Column("item_count")
        private int count;
    }
}
