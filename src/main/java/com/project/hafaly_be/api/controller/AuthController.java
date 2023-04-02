package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDto;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService userService;
    @PostMapping("/register")
    public ResponseClient register(@RequestBody @Valid UserDto user){
        return new ResponseClient(HttpStatus.OK, userService.register(user));
    }

    @PostMapping("/authenticate")
    public ResponseClient authenticate(@RequestBody @Valid RequestLogin requestLogin){
        return new ResponseClient(HttpStatus.OK, userService.authenticate(requestLogin));
    }

}
