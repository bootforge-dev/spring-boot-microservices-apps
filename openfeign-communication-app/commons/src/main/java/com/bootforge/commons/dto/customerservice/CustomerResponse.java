package com.bootforge.commons.dto.customerservice;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        CustomerStatus status
) {
}
