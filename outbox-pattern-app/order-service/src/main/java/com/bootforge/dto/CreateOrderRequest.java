package com.bootforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateOrderRequest(

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "CustomerId is required")
        @Positive(message = "CustomerId must be positive")
        Long customerId,

        @NotBlank(message = "Product Type is required")
        String productType,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Integer quantity
) {
}
