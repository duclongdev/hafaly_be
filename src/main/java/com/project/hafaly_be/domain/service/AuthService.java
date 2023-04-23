package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.AuthDTO;
import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.api.response.AuthResponse;


public interface AuthService {
    AuthResponse register(UserDTO userDto) ;
    AuthResponse authenticate(RequestLogin requestLogin);
    AuthResponse validateUser(AuthDTO userValidate);

    AuthResponse generateNewAccessToken(AuthDTO authDTO);

    AuthResponse verify(AuthDTO authDTO, String accessToken);
}
