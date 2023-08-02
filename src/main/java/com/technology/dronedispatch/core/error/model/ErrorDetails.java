package com.technology.dronedispatch.core.error.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ErrorDetails implements Serializable {

    private Date timestamp;
    private String message;
    private String data;
    private int code;
    private List<ValidationError> validation;

    public ErrorDetails(Date timestamp, String message, String data, int code) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ErrorDetails(Date timestamp, String message, String data, int code, List<ValidationError> validation) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = data;
        this.code = code;
        this.validation = validation;
    }
}
