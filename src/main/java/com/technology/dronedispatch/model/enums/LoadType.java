package com.technology.dronedispatch.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoadType {

    DEVICE("DEVICE"),
    SMALL_LOAD("SMALL_LOAD");

    private final String loadType;
}