package com.mmglowbeauty.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    public LocalDateTime timestamp;
    public int status;
    public String error;
    public String mensaje;
    public String path;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String mensaje, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.path = path;
    }
}
