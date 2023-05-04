package com.project.hafaly_be.domain.service.impl;

import com.project.hafaly_be.api.dto.UserDTO;
import com.project.hafaly_be.api.exception.customError.AfterHandlerException;
import com.project.hafaly_be.api.exception.customError.EntityNotFoundException;
import com.project.hafaly_be.api.exception.customError.UserNotFoundException;
import com.project.hafaly_be.domain.enums.Role;
import com.project.hafaly_be.domain.model.Family;
import com.project.hafaly_be.domain.model.User;
import com.project.hafaly_be.domain.repository.UserRepository;
import com.project.hafaly_be.domain.service.FamilyService;
import com.project.hafaly_be.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Boolean checkUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(()-> new UserNotFoundException(userId)) ;
    }

    @Override
    public User getUserByEmail(String email) {
       return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(email));
    }

    @Override
    public UserDTO updateUserRole(String id, Role role) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(()->new UserNotFoundException(id));
        user.setRole(role);
        userRepository.save(user);
        System.out.println(user.getRole());
        return UserDTO.builder().email(user.getEmail()).firstName(user.getFirstname()).lastName(user.getLastname())
                .build();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException(userEmail));
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(String.valueOf(userId)));
    }
}
