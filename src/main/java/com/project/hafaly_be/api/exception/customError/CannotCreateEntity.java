package com.project.hafaly_be.api.exception.customError;

import com.project.hafaly_be.domain.enums.Entity;

import java.text.MessageFormat;

public class CannotCreateEntity extends RuntimeException{
    private String message = "Can't not create Entity";
    public CannotCreateEntity(){
        super();
    }
    public CannotCreateEntity(Enum<Entity> entityEnum){
        super();
        initMessage(entityEnum);
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void initMessage(Enum<Entity> entityEnum) {
        if(entityEnum == Entity.USER)
            message = "user can't not create";
    }


}
