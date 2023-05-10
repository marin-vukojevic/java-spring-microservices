package com.decodetamination.item;

import com.decodetamination.api.item.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface ItemMapper {

    ItemDto map(Item item);
}
