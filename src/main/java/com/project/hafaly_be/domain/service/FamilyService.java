package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.CreateFamilyDTO;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.model.Family;

public interface FamilyService {
    ResponseClient create(CreateFamilyDTO createFamilyDTO);

    ResponseClient getCode();

    Family findByCode(String familyCode);
}