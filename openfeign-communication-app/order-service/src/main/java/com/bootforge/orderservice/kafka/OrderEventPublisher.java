package com.bootforge.orderservice.kafka;

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

    public static final String ORDER_CREATED_TOPIC = "order-created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrderCreated(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getQuantity()
        );

        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                String.valueOf(order.getId()),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {
                log.error(
                        "Failed to publish OrderCreatedEvent. orderId={}",
                        order.getId(),
                        exception
                );
                return;
            }

            log.info(
                    "OrderCreatedEvent published. orderId={}, topic={}, partition={}, offset={}",
                    order.getId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }
}
