package com.project.hafaly_be.api.exception;

import com.project.hafaly_be.api.exception.customError.*;
import com.project.hafaly_be.api.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerInvalidArgumentException(InvalidArgumentException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerDisabledException(DisabledException ex){
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFoundException(UserNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadCredentialsException(BadCredentialsException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PasswordErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerPasswordError(PasswordErrorException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handlerAccessDeniedException(AccessDeniedException ex){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerLockedException(LockedException ex){
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }
    @ExceptionHandler(AccountExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerAccountExpired(AccessDeniedException ex){
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }
    @ExceptionHandler(CredentialsExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerCredentialsExpiredException(CredentialsExpiredException ex){
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(CannotCreateUser.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerCannotCreateUser(CannotCreateUser ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
