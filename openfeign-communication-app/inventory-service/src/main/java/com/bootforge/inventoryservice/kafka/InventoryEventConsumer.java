package com.bootforge.inventoryservice.kafka;

import com.bootforge.commons.event.OrderCreatedEvent;
import com.bootforge.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-service"
    )
    public void consume(OrderCreatedEvent event) {
        inventoryService.reserve(event.productId(), event.quantity());
    }
}
