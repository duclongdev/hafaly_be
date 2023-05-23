package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.TaskDto;
import com.project.hafaly_be.api.response.ResponseClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    @GetMapping("/getAll/{familyId}")
    public ResponseEntity<ResponseClient> getAllTasks(@PathVariable("familyId") String familyId){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "All tasks");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
    @PostMapping("/add/{familyId}")
    public ResponseEntity<ResponseClient> addTask(@PathVariable("familyId") String familyId, TaskDto taskDto){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "All tasks");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
    @PutMapping("/update/")
    public ResponseEntity<ResponseClient> updateTask(TaskDto taskDto){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "update tasks");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }

    @DeleteMapping("/delete/{familyId}")
    public ResponseEntity<ResponseClient> deleteTask(@PathVariable("familyId") String familyId, String taskId){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "delete tasks");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }

}
