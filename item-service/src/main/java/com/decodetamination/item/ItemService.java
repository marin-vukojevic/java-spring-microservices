package com.decodetamination.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ItemService {

    private final ItemsDao itemsDao;

    public Item get(UUID itemUuid) {
        Item item = itemsDao.findById(itemUuid).orElseGet(() -> createItem(itemUuid));
        log.info("Found item {}.", item.getUuid());
        return item;
    }

    private Item createItem(UUID itemUuid) {
        return itemsDao.save(
                Item.builder()
                        .uuid(itemUuid)
                        .name(RandomStringUtils.randomAlphabetic(10))
                        .price(RandomUtils.nextInt(1000))
                        .build());
    }
}
