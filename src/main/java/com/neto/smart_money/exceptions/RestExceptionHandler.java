package com.neto.smart_money.exceptions;

import com.neto.smart_money.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(AccountNotFoundException.class)
    private ResponseEntity<ApiErrorMessage> accountNotFoundHandler(AccountNotFoundException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(InvalidTransactionAmountException.class)
    private ResponseEntity<ApiErrorMessage> invalidTransactionAmountHandler(InvalidTransactionAmountException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(CategoryDuplicateException.class)
    private ResponseEntity<ApiErrorMessage> categoryDuplicateHandler(CategoryDuplicateException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<ApiErrorMessage> categoryNotFoundHandler(CategoryNotFoundException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
    @ExceptionHandler(LaunchNotFoundException.class)
    private ResponseEntity<ApiErrorMessage> launchNotFoundHandler(LaunchNotFoundException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
    @ExceptionHandler(DifferentUserException.class)
    private ResponseEntity<ApiErrorMessage> differentUserHandler(DifferentUserException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
    @ExceptionHandler(UserNotAuthenticatedException.class)
    private ResponseEntity<ApiErrorMessage> UserNotAuthenticatedHandler(UserNotAuthenticatedException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    private ResponseEntity<ApiErrorMessage> AccountAlreadyExistsHandler(AccountAlreadyExistsException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }
    @ExceptionHandler(LaunchAlreadyExistsException.class)
    private ResponseEntity<ApiErrorMessage> LaunchAlreadyExistsHandler(LaunchAlreadyExistsException e){
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }
}
