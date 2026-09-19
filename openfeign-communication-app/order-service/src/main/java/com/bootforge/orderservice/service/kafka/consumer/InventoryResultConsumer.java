package com.bootforge.orderservice.service.kafka.consumer;

import com.bootforge.commons.dto.orderservice.OrderStatus;
import com.bootforge.commons.event.InventoryResultEvent;
import com.bootforge.commons.exception.order.OrderNotFoundException;
import com.bootforge.orderservice.entity.Order;
import com.bootforge.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryResultConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-topic",
            groupId = "order-group"
    )
    public void consume(InventoryResultEvent event) {
        log.info(
                "Received InventoryResultEvent. orderId={}, available={}",
                event.orderId(),
                event.available()
        );

        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found: " + event.orderId())
                );
        if (event.available()) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }

        orderRepository.save(order);

        log.info(
                "Order status updated. orderId={}, status={}",
                order.getId(),
                order.getStatus()
        );
    }

}

