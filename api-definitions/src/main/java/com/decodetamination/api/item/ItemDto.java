package com.decodetamination.api.item;

import java.util.UUID;

public record ItemDto(UUID uuid, String name, int price) {
}
