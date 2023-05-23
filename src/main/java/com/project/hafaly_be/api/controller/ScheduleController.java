package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.ScheduleDto;
import com.project.hafaly_be.api.response.ResponseClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/schedule")
@RestController
public class ScheduleController {
    @GetMapping("getAll/{familyId}")
    public ResponseEntity<ResponseClient> getAllSchedule(@PathVariable("familyId") String familyId){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "get all schedules");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
    @PostMapping("add/{familyId}")
    public ResponseEntity<ResponseClient> addSchedule(@PathVariable("familyId") String familyId, ScheduleDto schedule){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "add");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
}
