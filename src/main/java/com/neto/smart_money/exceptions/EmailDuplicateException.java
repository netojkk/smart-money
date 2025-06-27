package com.neto.smart_money.exceptions;

public class EmailDuplicateException extends RuntimeException{
    public EmailDuplicateException(String message){
        super(message);
    }
}
