package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping("{userId}/update-role")
    public ResponseClient updateRole(@PathVariable String userId){
        return new ResponseClient(HttpStatus.OK, userService.updateUserRole(userId));
    }
}
