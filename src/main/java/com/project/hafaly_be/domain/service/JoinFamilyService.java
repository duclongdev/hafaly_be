package com.project.hafaly_be.domain.service;

import com.project.hafaly_be.api.dto.JoinFamilyDTO;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.enums.StatusRequest;

public interface JoinFamilyService {
    ResponseClient sendRequest(JoinFamilyDTO joinFamilyDTO);
    ResponseClient handleRequest(String idRequest, StatusRequest typeRequest);
    ResponseClient getAllRequest(String familyId);
}
