package com.project.hafaly_be.api.controller;

import com.project.hafaly_be.api.dto.NoteDto;
import com.project.hafaly_be.api.response.ResponseClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/note")
public class NoteController {

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<ResponseClient> getAllNote(@PathVariable("userId") String userId){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "get note by user id");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }

    @PostMapping("add/{userId}")
    public ResponseEntity<ResponseClient> addNote(@PathVariable("userId") String userID, NoteDto noteDto){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "add note by user id");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
    @PutMapping("update/{noteId}")
    public ResponseEntity<ResponseClient> updateNote(@PathVariable("noteId") String noteId, NoteDto noteDto){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "update");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }

    @DeleteMapping("delete/{noteId}")
    public ResponseEntity<ResponseClient> deleteNote(@PathVariable("noteId") String noteId){
        ResponseClient responseClient = new ResponseClient(HttpStatus.OK, "delete");
        return ResponseEntity.status(responseClient.getHttpStatus()).body(responseClient);
    }
}
