package com.project.hafaly_be.domain.service.impl;

import com.project.hafaly_be.api.dto.RequestLogin;
import com.project.hafaly_be.api.dto.UserDto;
import com.project.hafaly_be.api.exception.customError.CannotCreateEntity;
import com.project.hafaly_be.api.exception.customError.PasswordErrorException;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.api.response.AuthResponse;
import com.project.hafaly_be.api.response.ErrorResponse;
import com.project.hafaly_be.api.security.JwtService;
import com.project.hafaly_be.domain.enums.Entity;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.Token;
import com.project.hafaly_be.domain.enums.TokenType;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.TokenRepository;
import com.project.hafaly_be.domain.repository.UserRepository;
import com.project.hafaly_be.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse register(UserDto userDto) {
        checkUser(userDto);
        User user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .role(Role.valueOf(userDto.getRole()))
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        var savedUser = userRepository.save(user);
        if(savedUser == null) throw new CannotCreateEntity(Entity.USER);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        var savedJwtRefreshTokenId = saveToken(user, jwtRefreshToken).getId();
        var jwtAccessToken = jwtService.generateAccessToken(user, String.valueOf(savedJwtRefreshTokenId));

        return AuthResponse.builder()
                .refreshToken(jwtRefreshToken).accessToken(jwtAccessToken)
                .build();
    }

    private void checkUser(UserDto userDto) {

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
        return AuthResponse
                .builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken).build();
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
