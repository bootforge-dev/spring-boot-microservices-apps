package com.bootforge.commons.event;

import lombok.Builder;

@Builder
public record InventoryResultEvent(
        Long orderId,
        Long productId,
        Integer quantity,
        boolean available,
        String reason
) {
}