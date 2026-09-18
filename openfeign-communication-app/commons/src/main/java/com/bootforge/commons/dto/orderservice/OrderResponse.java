package com.bootforge.commons.dto.orderservice;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long id,
        Long customerId,
        Long productId,
        Integer quantity,
        BigDecimal totalAmount,
        OrderStatus status
) {
}
