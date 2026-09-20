package com.bootforge.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
