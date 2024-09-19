package com.myblog1.payload;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;


@Getter
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String description;

    public ErrorResponse(String message, HttpStatus status, LocalDateTime timestamp, String description) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.description = description;
    }
}
