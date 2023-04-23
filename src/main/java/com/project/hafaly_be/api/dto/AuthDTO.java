package com.project.hafaly_be.api.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String token;
    private String refreshTokenId;
}
