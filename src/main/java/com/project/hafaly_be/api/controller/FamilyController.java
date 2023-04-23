package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.CreateFamilyDTO;
import com.project.hafaly_be.api.dto.EmailDto;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/family")
public class FamilyController {
    private final FamilyService familyService;
    @PostMapping("create")
    public ResponseEntity<ResponseClient> createFamily(@RequestBody CreateFamilyDTO createFamilyDTO){
        ResponseClient responseClient = familyService.create(createFamilyDTO);
        return ResponseEntity.status(responseClient.getHttpStatus())
                .body(responseClient);
    }
    @GetMapping("get-code")
    public ResponseEntity<ResponseClient> getCode() {
        ResponseClient responseClient = familyService.getCode();
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
}
