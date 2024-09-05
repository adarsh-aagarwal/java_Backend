package com.exaple.quiet_Q.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    private String message;
    private String error;
    private LocalDateTime localDateTime;

    public ErrorDetails() {
    }

    public ErrorDetails(String message, String error, LocalDateTime localDateTime) {
        this.message = message;
        this.error = error;
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
