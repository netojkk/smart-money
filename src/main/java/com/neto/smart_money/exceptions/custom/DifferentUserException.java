package com.neto.smart_money.exceptions.custom;

public class DifferentUserException extends RuntimeException {
    public DifferentUserException(String message) {
        super(message);
    }
}
