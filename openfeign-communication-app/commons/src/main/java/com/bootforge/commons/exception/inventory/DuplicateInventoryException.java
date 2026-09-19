package com.bootforge.commons.exception.inventory;

public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException(Long  productId) {
        super("Inventory already present with the productID: "+productId);
    }
}
