package com.bootforge.commons.dto.productservice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateProductRequest(

        @NotBlank(message = "Product name should be required")
        String name,

        @NotBlank(message = "Product description is required")
        String description,

        @NotNull(message = "Product price must be required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock must be enter")
        @Positive(message = "Stock must be positive")
        Integer stock
) {
}
