package com.project.hafaly_be.domain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hafaly_be.api.dto.AuthDTO;
import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.api.exception.customError.CannotCreateEntity;
import com.project.hafaly_be.api.exception.customError.CannotCreateUser;
import com.project.hafaly_be.api.exception.customError.EntityNotFoundException;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.api.response.AuthResponse;
import com.project.hafaly_be.api.security.JwtService;
import com.project.hafaly_be.domain.enums.Entity;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.Token;
import com.project.hafaly_be.domain.enums.TokenType;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.TokenRepository;
import com.project.hafaly_be.domain.repository.UserRepository;
import com.project.hafaly_be.domain.service.AuthService;
import com.project.hafaly_be.domain.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;


    @Override
    public AuthResponse register(UserDTO userDto) {
        checkUser(userDto);
        Date dateOfBirth = converDate(userDto.getDateOfBirth());
        userDto.setRole(Role.HOMELESS.name());
        User user = User.builder()
                .firstname(userDto.getFirstName())
                .lastname(userDto.getLastName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .gender(userDto.getGender())
                .address(userDto.getAddress())
                .dateOfBirth(dateOfBirth)
                .role(Role.valueOf(userDto.getRole()))
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        var savedUser = userRepository.save(user);
        if (savedUser == null) throw new CannotCreateEntity(Entity.USER);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        var savedJwtRefreshTokenId = saveToken(user, jwtRefreshToken).getId();
        var jwtAccessToken = jwtService.generateAccessToken(user, String.valueOf(savedJwtRefreshTokenId));

        return AuthResponse.builder()
                .accessToken(jwtAccessToken)
                .httpStatus(HttpStatus.OK)
                .refreshTokenId(savedJwtRefreshTokenId.toString())
                .userInfo(user)
                .message("login successful")
                .build();
    }

    private Date converDate(String dateOfBirth) {
        if (dateOfBirth == null) return new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return format.parse(dateOfBirth);
        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
        }
        return null;
    }

    private void checkUser(UserDTO userDto) {
        if (userService.checkUserExists(userDto.getEmail())) {
            throw new CannotCreateUser(userDto.getEmail());
        }
    }

    @Override
    public AuthResponse authenticate(RequestLogin requestLogin) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLogin.getEmail(),
                            requestLogin.getPassword()
                    )
            );
        } catch (DisabledException ex) {
            throw new DisabledException("Your account has been disable");
        } catch (LockedException ex) {
            throw new LockedException("Your account has been locked");
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        } catch (AccountExpiredException ex) {
            throw new AccountExpiredException("Your account has been expired");
        } catch (CredentialsExpiredException ex) {
            throw new CredentialsExpiredException("Your credentials have expired");
        }

        User user = userRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow();
        String refreshToken = jwtService.generateRefreshToken(user);

        String refreshTokenId = String.valueOf(saveToken(user, refreshToken).getId());
        String accessToken = jwtService.generateAccessToken(user, refreshTokenId);

        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .role(String.valueOf(user.getRole()))
                .build();
        return AuthResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .userInfo(userDTO)
                .refreshTokenId(refreshTokenId)
                .accessToken(accessToken).build();
    }

    @Override
    public AuthResponse validateUser(AuthDTO userValidate) {
        return null;
    }

    @Override
    public AuthResponse generateNewAccessToken(AuthDTO authDTO) {
        String email = authDTO.getEmail();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        Token refreshToken = tokenRepository.findById(UUID.fromString(authDTO.getRefreshTokenId())).orElseThrow(EntityNotFoundException::new);
        String newAccessToken = jwtService.generateAccessToken(userDetails, refreshToken.getId().toString());
        return AuthResponse.builder()
                .httpStatus(HttpStatus.OK)
                .refreshTokenId(refreshToken.getId().toString())
                .accessToken(newAccessToken)
                .message("new access token has been generated")
                .build();
    }

    @Override
    public AuthResponse verify(AuthDTO authDTO, String accessToken) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authDTO.getEmail());
        User user = userRepository.findByEmail(authDTO.getEmail()).orElseThrow(()->new UserNotFoundException(authDTO.getEmail()));
        Token refreshToken = tokenRepository.findById(UUID.fromString(authDTO.getRefreshTokenId())).orElseThrow(EntityNotFoundException::new);
        try{
            if(!jwtService.isTokenValid(refreshToken.getToken(), userDetails) || refreshToken.isExpired() || refreshToken.isRevoked()){
                return AuthResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                        .message("You must login")
                        .build();
            }
        }catch (ExpiredJwtException ex){
            return AuthResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                    .message("You must login")
                    .build();
        }

        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .build();

        try {
            if (jwtService.isTokenValid(accessToken, userDetails))
                return AuthResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .refreshTokenId(authDTO.getRefreshTokenId())
                        .message("verify token successful")
                        .accessToken(authDTO.getToken())
                        .userInfo(userDTO)
                        .build();
        } catch (SignatureException | ExpiredJwtException ex) {
            return AuthResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                    .message("Token has expired")
                    .build();
        }
        return AuthResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                .message("Token has expired")
                .build();
    }


    private Token saveToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        return tokenRepository.save(token);
    }
}
