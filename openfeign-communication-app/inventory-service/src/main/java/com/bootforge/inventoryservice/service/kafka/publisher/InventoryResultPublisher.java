package com.bootforge.inventoryservice.service.kafka.publisher;

import com.bootforge.commons.event.InventoryResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryResultPublisher {

    private final KafkaTemplate<String, InventoryResultEvent> kafkaTemplate;

    public void publish(InventoryResultEvent event) {
        kafkaTemplate.send(
                "inventory-topic",
                String.valueOf(event.orderId()),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {
                log.error("Failed to publish InventoryResultEvent. orderId={}",
                        event.orderId(),
                        exception
                );
                return;
            }

            log.info("InventoryResultEvent published. orderId={}, available={}",
                    event.orderId(),
                    event.available()
            );
        });
    }

}
