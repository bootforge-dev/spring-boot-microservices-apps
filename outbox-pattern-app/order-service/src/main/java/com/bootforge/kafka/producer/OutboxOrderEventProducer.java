package com.bootforge.kafka.producer;

import com.bootforge.constants.GlobalConstants;
import com.bootforge.entity.Outbox;
import com.bootforge.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxOrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOutboxOrder(Outbox outbox) {

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .aggregatedId(outbox.getAggregateId())
                .payload(outbox.getPayload())
                .build();

        kafkaTemplate.send(
                GlobalConstants.ORDER_CREATED_EVENT,
                event.aggregatedId(),
                event
        ).whenComplete((result, exception) -> {
            if (exception != null) {
                log.error(
                        "Failed to publish OrderCreatedEvent. aggregatedId={}",
                        outbox.getAggregateId(),
                        exception
                );
                return;
            }
            log.info(
                    "OrderCreatedEvent published successfully. " +
                            "aggregatedId={}, topic={}, partition={}, offset={}",
                    outbox.getAggregateId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }

}
