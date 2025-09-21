package com.quicken.orders.order_processing.exception;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);
    }
}
