package com.bootforge.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
=======

@SpringBootApplication
>>>>>>> 2b034dd (implemented inventory service)
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
