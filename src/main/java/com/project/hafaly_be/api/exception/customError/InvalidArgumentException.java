package com.project.hafaly_be.api.exception.customError;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class InvalidArgumentException extends RuntimeException{
    private final BindingResult bindingResult;
    public InvalidArgumentException(BindingResult bindingResult){
        this.bindingResult = bindingResult;
    }
}
