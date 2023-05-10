package com.decodetamination.order.items;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class ItemServiceUnavailable extends RuntimeException {
}
