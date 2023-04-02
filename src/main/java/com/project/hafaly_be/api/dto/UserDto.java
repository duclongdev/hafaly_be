package com.project.hafaly_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "{validation.user.password.not-empty}")
    private String firstname;

    @NotBlank(message = "{validation.user.email.not-empty}")
    private String lastname;
    @NotBlank(message = "{validation.user.password.not-empty}")
    private String password;
    @NotBlank(message = "{validation.user.password.not-empty}")
    @Email(message = "{validation.user.email}")
    private String email;
    private String role;
}
