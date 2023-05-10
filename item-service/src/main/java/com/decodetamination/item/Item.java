package com.decodetamination.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("items")
public class Item {

    @Id
    private UUID uuid;
    private String name;
    private int price;
}
