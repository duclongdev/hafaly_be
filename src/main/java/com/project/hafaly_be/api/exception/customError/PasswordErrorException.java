package com.project.hafaly_be.api.exception.customError;

import java.text.MessageFormat;

public class PasswordErrorException extends NotFoundException{
    private final static String MESSAGE_KEY = "user.password.error";
    public PasswordErrorException(String userEmail) {
        super(MessageFormat.format(getMessage(MESSAGE_KEY), userEmail));
    }

}
