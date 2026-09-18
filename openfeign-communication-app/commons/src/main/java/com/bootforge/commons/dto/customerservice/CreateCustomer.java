package com.bootforge.commons.dto.customerservice;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CreateCustomer(
        @NotBlank(message = "Customer name should be required")
        String name,

        @NotBlank(message = "Customer email is required")
        @Email(
                message = "Please provide a valid email address",
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )
        String email,

        @NotBlank(message = "Customer phone number is required")
        @Pattern(
                regexp = "^[6-9]\\d{9}$",
                message = "Please provide a valid 10-digit phone number"
        )
        String phone,

        @NotBlank(message = "Address must be required")
        String address
) {
}
