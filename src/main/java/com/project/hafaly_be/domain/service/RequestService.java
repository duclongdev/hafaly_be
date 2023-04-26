package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.JoinFamilyDTO;
import com.project.hafaly_be.api.response.ResponseClient;

public interface RequestService {
    ResponseClient joinFamily(String emailUer, String familyCode);
}
