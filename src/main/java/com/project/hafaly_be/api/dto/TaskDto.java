package com.project.hafaly_be.api.dto;

import java.util.Date;
import java.util.List;

public class TaskDto {
    private String name;
    private Date start;
    private Date end;
    private String type;
    private String priorityLevel;
    private String authorId;
    private String description;
    private List<String> assignments;

}
