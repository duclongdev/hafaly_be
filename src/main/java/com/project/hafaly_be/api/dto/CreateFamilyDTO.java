package com.project.hafaly_be.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateFamilyDTO {
    private String hostEmail;
    private String name;
    private String code;
    private String address;
    private String phoneNumber;
    @JsonIgnore
    private MultipartFile imageFile;
}
