package com.bootforge.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD
@Table(
        name = "inventory",
        uniqueConstraints = @UniqueConstraint(name = "uk_inventory_product", columnNames = "product_id")
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

=======
@Table(name = "inventories",
uniqueConstraints = {
        @UniqueConstraint(name = "uk_inventory_product", columnNames = "product_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Inventory {
>>>>>>> 2b034dd (implemented inventory service)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(name = "product_id", nullable = false)
=======
    @Column(nullable = false,name = "product_id")
>>>>>>> 2b034dd (implemented inventory service)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer reservedQuantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
<<<<<<< HEAD
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
=======
    public void prePersist(){
        LocalDateTime now  = LocalDateTime.now();
        createdAt  = now;
        updatedAt = now;

        if(reservedQuantity == null){
            reservedQuantity = 0;
        }
    }

    @PreUpdate
    public void preUpdate(){
>>>>>>> 2b034dd (implemented inventory service)
        updatedAt = LocalDateTime.now();
    }
}
