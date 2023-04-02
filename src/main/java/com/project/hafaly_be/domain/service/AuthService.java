package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDto;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.api.response.AuthResponse;


public interface AuthService {
    AuthResponse register(UserDto userDto);
    AuthResponse authenticate(RequestLogin requestLogin);
}
