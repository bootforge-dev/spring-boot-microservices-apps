package com.bootforge.commons.event;

import lombok.Builder;

@Builder
public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        Long productId,
        Integer quantity
) {}
