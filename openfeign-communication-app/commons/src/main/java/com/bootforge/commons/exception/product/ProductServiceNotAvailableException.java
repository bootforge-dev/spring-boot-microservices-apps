package com.bootforge.commons.exception.product;

public class ProductServiceNotAvailableException extends RuntimeException {
    public ProductServiceNotAvailableException(String message) {
        super(message);
    }
}
