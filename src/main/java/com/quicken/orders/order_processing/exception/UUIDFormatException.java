package com.quicken.orders.order_processing.exception;

public class UUIDFormatException extends RuntimeException {
    public UUIDFormatException(String message) {
        super(message);
    }
}
