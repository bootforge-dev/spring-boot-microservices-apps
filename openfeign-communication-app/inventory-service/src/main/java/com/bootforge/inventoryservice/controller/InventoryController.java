package com.bootforge.inventoryservice.controller;

import com.bootforge.commons.dto.inventory.CreateInventoryRequest;
import com.bootforge.commons.dto.inventory.InventoryResponse;
import com.bootforge.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody CreateInventoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.getByProductId(productId));
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<Void> reserve(
            @PathVariable Long productId,
            @RequestParam
            @Positive(message = "Quantity should be positive") Integer quantity
    ){
        inventoryService.reserve(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/release")
    public ResponseEntity<Void> release(
            @PathVariable Long productId,
            @RequestParam
            @Positive(message = "Quantity should be positive") Integer quantity
    ){
        inventoryService.release(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/add-stock")
    public ResponseEntity<Void> addStock(
            @PathVariable Long productId,
            @RequestParam
            @Positive(message = "Quantity should be positive") Integer quantity
    ){
        inventoryService.addStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/reomve-stock")
    public ResponseEntity<Void> removeStock(
            @PathVariable Long productId,
            @RequestParam
            @Positive(message = "Quantity should be positive") Integer quantity
    ){
        inventoryService.removeStock(productId, quantity);
        return ResponseEntity.ok().build();
    }





}
