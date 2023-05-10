package com.decodetamination.item;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemsDao extends CrudRepository<Item, UUID> {
}
