package com.neto.smart_money.exceptions.custom;

public class EmailDuplicateException extends RuntimeException{
    public EmailDuplicateException(String message){
        super(message);
    }
}
