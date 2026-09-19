package com.bootforge.inventoryservice.service.kafka.consumer;

import com.bootforge.commons.event.InventoryResultEvent;
import com.bootforge.commons.event.OrderCreatedEvent;
import com.bootforge.inventoryservice.service.InventoryService;
import com.bootforge.inventoryservice.service.kafka.publisher.InventoryResultPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {
    private final InventoryService inventoryService;
    private final InventoryResultPublisher inventoryResultPublisher;

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consume(OrderCreatedEvent event) {
        log.info(
                "Received OrderCreatedEvent. orderId={}, productId={}, quantity={}",
                event.orderId(),
                event.productId(),
                event.quantity()
        );
        try {
            inventoryService.reserve(event.productId(), event.quantity());

            inventoryResultPublisher.publish(
                    InventoryResultEvent.builder()
                            .orderId(event.orderId())
                            .productId(event.productId())
                            .quantity(event.quantity())
                            .available(true)
                            .reason("Inventory reserved successfully")
                            .build()
            );
        } catch (Exception ex) {
            log.error(
                    "Inventory reservation failed. orderId={}, productId={}",
                    event.orderId(),
                    event.productId(),
                    ex
            );
            inventoryResultPublisher.publish(
                    InventoryResultEvent.builder()
                            .orderId(event.orderId())
                            .productId(event.productId())
                            .quantity(event.quantity())
                            .available(false)
                            .reason(ex.getMessage())
                            .build()
            );
        }
    }
}
