package com.bootforge.service;

import com.bootforge.dto.CreateOrderRequest;
import com.bootforge.dto.OrderResponse;
import com.bootforge.entity.Order;
import com.bootforge.entity.Outbox;
import com.bootforge.repository.OrderRepository;
import com.bootforge.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;


    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        BigDecimal totalAmount = request.price().multiply(
                BigDecimal.valueOf(request.quantity())
        );
        Order order = Order.builder()
                .name(request.name())
                .customerId(request.customerId())
                .productType(request.productType())
                .price(request.price())
                .quantity(request.quantity())
                .totalAmount(totalAmount)
                .build();
        Order savedOrder = orderRepository.save(order);

        Outbox outbox = Outbox.builder()
                .aggregateId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(savedOrder))
                .processed(false)
                .build();
        outboxRepository.save(outbox);

        return toOrderResponse(savedOrder);
    }

    private OrderResponse toOrderResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .name(order.getName())
                .customerId(order.getCustomerId())
                .productType(order.getProductType())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
