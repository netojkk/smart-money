package com.neto.smart_money.infra;

import com.neto.smart_money.exceptions.EmailDuplicateException;
import com.neto.smart_money.exceptions.UserNotFoundException;
import com.neto.smart_money.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ApiErrorMessage> userNotFoundHandler(UserNotFoundException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    private ResponseEntity<ApiErrorMessage> duplicateEmailHandler(EmailDuplicateException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    private ResponseEntity<ApiErrorMessage> wrongPasswordHandler(WrongPasswordException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }
}
