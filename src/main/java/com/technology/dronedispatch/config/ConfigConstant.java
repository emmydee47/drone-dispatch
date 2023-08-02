package com.technology.dronedispatch.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigConstant {

    @Value("${max-drone-serial-no-character-length}")
    private int maxDroneSerialNoCharacterLength;

    @Value("${drone-fleet-size}")
    private int droneFleetSize;

    @Value("${sample-medication-count}")
    private int sampleMedicationCount;

    @Value("${drone-low-battery-percentage-threshold}")
    private int droneLowBatteryPercentageThreshold;
}
