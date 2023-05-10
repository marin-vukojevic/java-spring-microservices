package com.decodetamination.item;

import com.decodetamination.api.item.ItemApi;
import com.decodetamination.api.item.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ItemsController implements ItemApi {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto get(UUID itemUuid) {
        return itemMapper.map(itemService.get(itemUuid));
    }
}
