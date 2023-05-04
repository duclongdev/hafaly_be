package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.User;

import java.util.UUID;

public interface UserService {
    Boolean checkUserExists(String email);

    User getUserById(String userId);
    User getUserByEmail(String email);
    UserDTO updateUserRole(String userId, Role role);

    void save(User user);

    User findByEmail(String userEmail);

    User findById(UUID userId);
}
