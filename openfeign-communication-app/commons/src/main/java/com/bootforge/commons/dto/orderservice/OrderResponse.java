package com.bootforge.commons.dto.orderservice;

import com.bootforge.commons.dto.customerservice.CustomerResponse;
import com.bootforge.commons.dto.productservice.ProductResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long id,
        CustomerResponse customer,
        ProductResponse productId,
        Integer quantity,
        BigDecimal totalAmount,
        OrderStatus status
) {
}
