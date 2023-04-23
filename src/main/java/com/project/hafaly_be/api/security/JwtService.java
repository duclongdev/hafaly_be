package com.project.hafaly_be.api.security;

import com.project.hafaly_be.domain.model.Token;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateRefreshToken(UserDetails userDetails);
    String generateAccessToken(UserDetails userDetails, String RefreshTokenId);
    String extractUsername(String token);
    String exactRefreshTokenId(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
