package com.bootforge.kafka.producer;

import com.bootforge.constants.GlobalConstants;
import com.bootforge.entity.Order;
import com.bootforge.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrder(Order order){

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .build();

        kafkaTemplate.send(
                GlobalConstants.ORDER_CREATED_EVENT,
                String.valueOf(event.orderId()),
                event
        ).whenComplete((result, exception) -> {
            if(exception != null){
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
