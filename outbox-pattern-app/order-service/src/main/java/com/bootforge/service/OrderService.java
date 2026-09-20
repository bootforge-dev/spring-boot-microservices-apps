package com.bootforge.service;

import com.bootforge.dto.CreateOrderRequest;
import com.bootforge.dto.OrderResponse;
import com.bootforge.entity.Order;
import com.bootforge.kafka.producer.OrderEventProducer;
import com.bootforge.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderProducer;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        BigDecimal totalAmount = BigDecimal.valueOf(100).multiply(
                BigDecimal.valueOf(request.quantity())
        );
        Order order = Order.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .totalAmount(totalAmount)
                .build();
        Order savedOrder = orderRepository.save(order);

        //sent OrderCreatedEvent to kafka topic
        orderProducer.publishOrder(savedOrder);

        return toOrderResponse(savedOrder);
    }

    private OrderResponse toOrderResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
