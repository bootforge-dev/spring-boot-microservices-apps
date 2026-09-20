package com.bootforge.event;

import lombok.Builder;

@Builder
public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Integer quantity
) {
}
