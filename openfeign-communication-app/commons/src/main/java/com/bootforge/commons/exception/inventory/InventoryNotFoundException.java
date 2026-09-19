package com.bootforge.commons.exception.inventory;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(Long productId) {
        super("Inventory not found for productId: " + productId);

    }
}