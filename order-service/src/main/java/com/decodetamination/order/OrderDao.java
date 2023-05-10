package com.decodetamination.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDao extends CrudRepository<Order, UUID> {
}
