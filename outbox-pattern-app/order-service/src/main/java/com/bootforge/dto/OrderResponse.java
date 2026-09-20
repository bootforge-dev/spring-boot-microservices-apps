package com.bootforge.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Long id,
        String name,
        Long customerId,
        String productType,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
