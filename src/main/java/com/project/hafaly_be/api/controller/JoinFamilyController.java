package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.JoinFamilyDTO;
import com.project.hafaly_be.api.response.ResponseClient;
import com.project.hafaly_be.domain.enums.StatusRequest;
import com.project.hafaly_be.domain.service.JoinFamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/join-family")
public class JoinFamilyController {

    private final JoinFamilyService joinFamilyService;

    @PostMapping
    public ResponseEntity<ResponseClient> sendRequest(@RequestBody JoinFamilyDTO joinFamilyDTO) {
        ResponseClient client = joinFamilyService.sendRequest(joinFamilyDTO);
        return ResponseEntity.status(client.getHttpStatus()).body(client);
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponseClient> getAllRequest(@PathVariable("id") String id) {
        ResponseClient client = joinFamilyService.getAllRequest(id);
        return ResponseEntity.status(client.getHttpStatus()).body(client);
    }
    @PutMapping("accept/{id}")
    public ResponseEntity<ResponseClient> acceptRequest(@PathVariable("id") String id){
        ResponseClient client = joinFamilyService.handleRequest(id, StatusRequest.ACCEPT);
        return ResponseEntity.status(client.getHttpStatus()).body(client);
    }
    @PutMapping("refuse/{id}")
    public ResponseEntity<ResponseClient> refuseRequest(@PathVariable("id") String id){
        ResponseClient client = joinFamilyService.handleRequest(id, StatusRequest.REFUSE);
        return ResponseEntity.status(client.getHttpStatus()).body(client);
    }

//    @PutMapping("accept/{id}")
//    public ResponseEntity<String> acceptRequest(@PathVariable("id") String id){
////        ResponseClient client = joinFamilyService.handleRequest(id, StatusRequest.ACCEPT);
//        return ResponseEntity.status(HttpStatus.OK).body(id);
//    }
}
