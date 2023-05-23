package com.project.hafaly_be.api.dto;

import lombok.Data;

@Data
public class NoteDto {
    private String note;
    private String description;
    private String authorId;
    private String dueDate;
}
