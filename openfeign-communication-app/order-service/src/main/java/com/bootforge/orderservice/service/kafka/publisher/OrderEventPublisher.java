package com.bootforge.orderservice.service.kafka.publisher;

import com.bootforge.commons.event.OrderCreatedEvent;
import com.bootforge.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrder(Order order) {
        OrderCreatedEvent orderEvent = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .build();

        kafkaTemplate.send("order-created",
                String.valueOf(order.getId()),
                orderEvent).whenComplete((result, exception) -> {
            if (exception != null) {
                log.error(
                        "Failed to publish OrderCreatedEvent. orderId={}",
                        order.getId(),
                        exception
                );
                return;
            }
            log.info(
                    "OrderCreatedEvent published successfully. " +
                            "orderId={}, topic={}, partition={}, offset={}",
                    order.getId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }

}
