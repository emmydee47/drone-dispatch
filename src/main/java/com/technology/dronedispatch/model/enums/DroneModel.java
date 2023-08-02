package com.technology.dronedispatch.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@Getter
@AllArgsConstructor
public enum DroneModel {

    LIGHTWEIGHT("LIGHTWEIGHT"),
    MIDDLEWEIGHT("MIDDLEWEIGHT"),
    CRUISER_WEIGHT("CRUISER_WEIGHT"),
    HEAVYWEIGHT("HEAVYWEIGHT");

    private final String model;

    public static DroneModel getRandomModel() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}