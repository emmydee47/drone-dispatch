package com.technology.dronedispatch.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.technology.dronedispatch.model.enums.DroneModel;
import com.technology.dronedispatch.model.validation.javax_rules.NotEnum;
import com.technology.dronedispatch.model.validation.order.FirstOrder;
import com.technology.dronedispatch.model.validation.order.FourthOrder;
import com.technology.dronedispatch.model.validation.order.SecondOrder;
import com.technology.dronedispatch.model.validation.order.ThirdOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@GroupSequence({DroneRegisterRequest.class, FirstOrder.class,
        SecondOrder.class, ThirdOrder.class, FourthOrder.class})
public class DroneRegisterRequest {

    @JsonProperty("droneModel")
    @NotNull(message = "Drone Model must be provided ! INVALID", groups = FirstOrder.class)
    @NotBlank(message = "Drone Model is blank ! BLANK", groups = SecondOrder.class)
    @NotEnum(enumClass = DroneModel.class, message = "Drone Model is invalid, " +
            "check 'DroneModel' in the enums ! INVALID'",
            groups = ThirdOrder.class)
    private String model;

    @NotNull(message = "Please provide the Drone Weight ! INVALID", groups = FirstOrder.class)
    @DecimalMin(value = "1.00", message = "Drone weight must not be less than 1.0  ! INVALID", groups = SecondOrder.class)
    @DecimalMax(value = "500.00", message = "Drone weight cannot be more than 500gr ! INVALID", groups = SecondOrder.class)
    private double weightLimit;
}
