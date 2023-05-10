package com.decodetamination.order.items;

import com.decodetamination.api.item.ItemApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface ItemClient extends ItemApi {
}
