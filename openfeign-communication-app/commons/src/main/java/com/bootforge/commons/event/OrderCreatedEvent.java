package com.bootforge.commons.event;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        Long productId,
        Integer quantity
) {}
