package com.neto.smart_money.exceptions.custom;

public class LaunchAlreadyExistsException extends RuntimeException {
    public LaunchAlreadyExistsException(String message) {
        super(message);
    }
}
