package com.project.hafaly_be.api.dto;

import lombok.Data;

@Data
public class RequestLogin {
    private String email;
    private String password;
}
