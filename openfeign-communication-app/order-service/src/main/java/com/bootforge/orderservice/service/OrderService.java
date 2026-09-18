package com.bootforge.orderservice.service;

import com.bootforge.commons.dto.customerservice.CustomerResponse;
import com.bootforge.commons.dto.orderservice.CreateOrderRequest;
import com.bootforge.commons.dto.orderservice.OrderResponse;
import com.bootforge.commons.dto.orderservice.OrderStatus;
import com.bootforge.commons.dto.productservice.ProductResponse;
import com.bootforge.commons.exception.customer.CustomerNotFoundException;
import com.bootforge.commons.exception.product.ProductNotFoundException;
import com.bootforge.orderservice.client.CustomerClient;
import com.bootforge.orderservice.client.ProductClient;
import com.bootforge.orderservice.entity.Order;
import com.bootforge.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public OrderResponse createOrder(CreateOrderRequest request) {

        CustomerResponse customer = customerClient.getCustomer(request.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found for customerId: " + request.customerId());
        }

        ProductResponse product = productClient.getProductById(request.productId());
        if (product == null) {
            throw new ProductNotFoundException("Product not found for productId: " + request.productId());
        }

        BigDecimal totalAmount = product.price().multiply(
                BigDecimal.valueOf(request.quantity())
        );

        Order order = Order.builder()
                .customerId(customer.id())
                .productId(product.id())
                .quantity(request.quantity())
                .totalAmount(totalAmount)
                .status(OrderStatus.CREATED)
                .build();
        Order savedOrder = orderRepository.save(order);

        return getOrderResponse(savedOrder, customer, product);
    }

    private static OrderResponse getOrderResponse(Order savedOrder, CustomerResponse customer, ProductResponse product) {
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .customer(customer)
                .productId(product)
                .quantity(savedOrder.getQuantity())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .build();
    }

}
