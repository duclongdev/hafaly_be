package com.project.hafaly_be.api.dto;

import lombok.Data;

@Data
public class CreateFamilyDTO {
    private String hostEmail;
    private String family;
    private String name;
    private String code;
    private String address;
    private String phoneNumber;
}
