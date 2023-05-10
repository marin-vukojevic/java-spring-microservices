package com.decodetamination.api.item;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface ItemApi {

    @GetMapping("items/{itemUuid}")
    ItemDto get(@PathVariable UUID itemUuid);
}
