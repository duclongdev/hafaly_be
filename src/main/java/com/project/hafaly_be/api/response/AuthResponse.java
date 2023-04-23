package com.project.hafaly_be.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hafaly_be.api.dto.UserDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class AuthResponse {
    @JsonIgnore
    private HttpStatus httpStatus;
    private Object userInfo;
    private String accessToken;
    private String refreshTokenId;
    private String message;
}
