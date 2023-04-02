package com.project.hafaly_be.api.exception.customError;

import com.project.hafaly_be.domain.enums.Entity;

public class CannotCreateEntity extends RuntimeException{
    private String message;
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
