package com.project.hafaly_be.api.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class RequestLogin {
    @NotBlank(message = "{validation.user.email.not-empty}")
    private String email;
    @NotBlank(message = "{validation.user.password.not-empty}")
    private String password;
}
