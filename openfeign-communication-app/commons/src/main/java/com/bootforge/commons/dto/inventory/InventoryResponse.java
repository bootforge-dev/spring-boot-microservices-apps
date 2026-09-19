package com.bootforge.commons.dto.inventory;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InventoryResponse(
        Long id,
        Long productId,
        Integer quantity,
        Integer reservedQuantity,
        Integer availableQuantity
) {
}
