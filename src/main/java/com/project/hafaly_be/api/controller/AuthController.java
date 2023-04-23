package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.AuthDTO;
import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.api.response.AuthResponse;
import com.project.hafaly_be.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.project.hafaly_be.api.exception.customError.InvalidArgumentException;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDTO user) {
        AuthResponse authResponse = authService.register(user);
        System.out.println(user.getDateOfBirth());
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid RequestLogin requestLogin,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidArgumentException(bindingResult);
        }
        return  authService.authenticate(requestLogin);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthDTO authDTO) {
        return authService.validateUser(authDTO);
    }
    @PostMapping("verify")
    public ResponseEntity<AuthResponse> verify(@RequestBody AuthDTO authDTO, @RequestHeader("Authorization") String authorizationHeader){
        String[] authorizationHeaderParts = authorizationHeader.split(" ");
        String accessToken = authorizationHeaderParts[1];
        AuthResponse authResponse = authService.verify(authDTO, accessToken);
        return ResponseEntity.status(authResponse.getHttpStatus()).body(authResponse) ;
    }

    @PostMapping("/new-access-token")
    public ResponseEntity<AuthResponse> generateNewAccessToken(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.status(HttpStatus.OK).body( authService.generateNewAccessToken(authDTO));

    }

}
