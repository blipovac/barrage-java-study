package com.setronica.eventing.dto;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class ErrorResponseDto {
  private ZonedDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

    public ErrorResponseDto(int status, String error, String message, String path) {
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
