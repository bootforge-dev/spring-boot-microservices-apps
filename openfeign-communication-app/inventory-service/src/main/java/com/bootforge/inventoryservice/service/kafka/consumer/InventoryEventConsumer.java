package com.bootforge.inventoryservice.service.kafka.consumer;

import com.bootforge.commons.event.OrderCreatedEvent;
import com.bootforge.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {
    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-created",groupId = "inventory-group")
    public void consume(OrderCreatedEvent event){
        log.info(
                "Received OrderCreatedEvent. orderId={}, productId={}, quantity={}",
                event.orderId(),
                event.productId(),
                event.quantity()
        );
        inventoryService.reserve(event.productId(), event.quantity());
        log.info(
                "Inventory reserved successfully. orderId={}, productId={}, quantity={}",
                event.orderId(),
                event.productId(),
                event.quantity()
        );
    }
}
