package com.bootforge.commons.exception.customer;

public class CustomerServiceNotAvailableException extends RuntimeException {
    public CustomerServiceNotAvailableException(String message) {
        super(message);
    }
}
