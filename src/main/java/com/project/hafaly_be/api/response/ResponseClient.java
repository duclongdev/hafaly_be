package com.project.hafaly_be.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ResponseClient {
    @JsonIgnore
    private HttpStatus httpStatus;
    private Object data;
}
