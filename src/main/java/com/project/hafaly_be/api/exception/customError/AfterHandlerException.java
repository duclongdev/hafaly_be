package com.project.hafaly_be.api.exception.customError;

import org.springframework.http.HttpStatus;

public class AfterHandlerException extends RuntimeException{
    private HttpStatus code;
    public AfterHandlerException(HttpStatus code, String message){
        super(message);
        this.code = code;
    }
    public HttpStatus getCode(){
        return code;
    }

}
