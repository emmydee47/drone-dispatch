package com.technology.dronedispatch.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZoneId;

@Data
public class JobScheduleRequest {

    @NotNull
    private Integer intervalInSec;

    @NotNull
    private Integer count;

    @NotNull
    private ZoneId timeZone;

}
