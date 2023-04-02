package com.project.hafaly_be.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ResponseClient {
    private HttpStatus httpStatus;
    private Object data;
}
