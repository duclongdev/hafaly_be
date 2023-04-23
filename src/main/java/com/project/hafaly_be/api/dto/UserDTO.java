package com.project.hafaly_be.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
public class UserDTO {
    @NotBlank(message = "{validation.user.password.not-empty}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @NotBlank(message = "{validation.user.email.not-empty}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @NotBlank(message = "{validation.user.password.not-empty}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dateOfBirth;

    @Email(message = "{validation.user.email}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean gender;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
}
