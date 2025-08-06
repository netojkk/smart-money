package com.neto.smart_money.exceptions.custom;

public class InvalidCategoryTypeException extends RuntimeException {
    public InvalidCategoryTypeException(String message) {
        super(message);
    }
}
