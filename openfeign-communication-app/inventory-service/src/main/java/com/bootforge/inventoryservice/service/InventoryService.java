package com.bootforge.inventoryservice.service;

<<<<<<< HEAD
=======
import com.bootforge.commons.dto.inventory.CreateInventoryRequest;
import com.bootforge.commons.dto.inventory.InventoryResponse;
import com.bootforge.commons.exception.inventory.DuplicateInventoryException;
import com.bootforge.commons.exception.inventory.InsufficientInventoryException;
import com.bootforge.commons.exception.inventory.InventoryNotFoundException;
>>>>>>> 2b034dd (implemented inventory service)
import com.bootforge.inventoryservice.entity.Inventory;
import com.bootforge.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
<<<<<<< HEAD
    public void reserve(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "Inventory not found for productId: " + productId));

        int available = inventory.getQuantity() - inventory.getReservedQuantity();

        if (available < quantity) {
            throw new IllegalStateException(
                    "Insufficient inventory for productId: " + productId);
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventoryRepository.save(inventory);
    }
=======
    public InventoryResponse createInventory(CreateInventoryRequest request) {
        if (inventoryRepository.existsByProductId(request.productId())) {
            throw new DuplicateInventoryException(request.productId());
        }
        Inventory inventory = Inventory.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
        Inventory savedInventory = inventoryRepository.save(inventory);
        return toInventoryResponse(savedInventory);
    }

    @Transactional(readOnly = true)
    public InventoryResponse getByProductId(Long productId) {
        return toInventoryResponse(getInventory(productId));
    }


    @Transactional
    public void release(Long productId, Integer quantity) {
        Inventory inventory = getInventory(productId);
        if (inventory.getReservedQuantity() < quantity) {
            throw new IllegalStateException("Cannot release more than reserved quantity");
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void addStock(Long productId, Integer quantity) {
        Inventory inventory = getInventory(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);
    }

    @Transactional
    public void removeStock(Long productId, Integer quantity) {
        Inventory inventory = getInventory(productId);
        int availableQuantity =
                inventory.getQuantity() - inventory.getReservedQuantity();
        if (availableQuantity < quantity) {
            throw new InsufficientInventoryException(productId, quantity, availableQuantity);
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void reserve(Long productId, Integer quantity) {
        Inventory inventory = getInventory(productId);
        int availableQuantity =
                inventory.getQuantity() - inventory.getReservedQuantity();
        if (availableQuantity < quantity) {
            throw new InsufficientInventoryException(productId, quantity, availableQuantity);
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    private Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new InventoryNotFoundException(productId)
        );
    }

    private InventoryResponse toInventoryResponse(Inventory inventory) {
        int availableQuantity =
                inventory.getQuantity() - inventory.getReservedQuantity();

        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .quantity(inventory.getQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .availableQuantity(availableQuantity)
                .build();
    }
>>>>>>> 2b034dd (implemented inventory service)
}
