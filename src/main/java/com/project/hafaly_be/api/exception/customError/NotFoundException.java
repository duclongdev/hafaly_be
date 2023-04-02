package com.project.hafaly_be.api.exception.customError;

import com.project.hafaly_be.domain.enums.Entity;
import java.util.ResourceBundle;

public abstract class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }

    protected static String getMessage(String messageKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        return bundle.getString(messageKey);
    }
}

