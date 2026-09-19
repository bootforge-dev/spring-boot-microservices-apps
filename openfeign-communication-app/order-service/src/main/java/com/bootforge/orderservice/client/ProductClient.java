package com.bootforge.orderservice.client;

import com.bootforge.commons.dto.productservice.ProductResponse;
import com.bootforge.orderservice.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "product-service",
        configuration = FeignRetryConfig.class
)
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductResponse getProductById(@PathVariable Long id);

}
