package com.technology.dronedispatch.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {

    CAMERA("CAMERA"),
    HEADPHONE("HEADPHONE"),
    OTHER("OTHER");

    private final String loadType;
}