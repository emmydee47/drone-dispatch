package com.technology.dronedispatch.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private T data;
    private String statusCode;
    private String statusMessage;
    private Object error;

    public ResponseDto(T data, String statusCode, String statusMessage){
        this.data = data;
        this.statusCode=statusCode;
        this.statusMessage=statusMessage;
    }

    public ResponseDto(String statusCode, String statusMessage){
        this.statusCode=statusCode;
        this.statusMessage=statusMessage;
    }
}
