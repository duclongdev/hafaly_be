package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.domain.model.User;

public interface UserService {
    Boolean checkUserExists(String email);

    User getUserById(String userId);
    User getUserByEmail(String email);
    UserDTO updateUserRole(String email);

    void save(User user);
}
