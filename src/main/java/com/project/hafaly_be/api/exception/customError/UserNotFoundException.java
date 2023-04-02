package com.project.hafaly_be.api.exception.customError;

import java.text.MessageFormat;

public class UserNotFoundException extends NotFoundException{
    private static final String MESSAGE_KEY = "user.not.found.error";
    public UserNotFoundException(String userId) {
        super(MessageFormat.format(getMessage(MESSAGE_KEY), userId));
    }
}
