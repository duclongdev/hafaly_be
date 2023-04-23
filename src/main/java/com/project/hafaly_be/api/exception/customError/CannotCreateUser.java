package com.project.hafaly_be.api.exception.customError;

import java.text.MessageFormat;

public  class CannotCreateUser extends NotFoundException {
    private static final String MESSAGE_KEY = "user.cannot.create";
    public CannotCreateUser(String userId) {
        super(MessageFormat.format(getMessage(MESSAGE_KEY), userId));
    }
}

