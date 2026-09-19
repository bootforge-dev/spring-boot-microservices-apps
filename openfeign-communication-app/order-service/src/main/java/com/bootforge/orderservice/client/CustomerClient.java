package com.bootforge.orderservice.client;

import com.bootforge.commons.dto.customerservice.CustomerResponse;
import com.bootforge.orderservice.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-service",
        configuration = FeignRetryConfig.class
)
public interface CustomerClient {

    @GetMapping("/api/v1/customers/{id}")
    CustomerResponse getCustomer(@PathVariable Long id);

}
