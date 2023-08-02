package com.technology.dronedispatch.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technology.dronedispatch.model.validation.order.FirstOrder;
import com.technology.dronedispatch.model.validation.order.FourthOrder;
import com.technology.dronedispatch.model.validation.order.SecondOrder;
import com.technology.dronedispatch.model.validation.order.ThirdOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@GroupSequence({DroneLoadingRequest.class, FirstOrder.class,
        SecondOrder.class, ThirdOrder.class, FourthOrder.class})
public class DroneLoadingRequest {

    @NotNull(message = "Drone Serial Number must be provided ! INVALID", groups = FirstOrder.class)
    @NotBlank(message = "Drone Serial Number is blank ! BLANK", groups = SecondOrder.class)
    private String droneSerialNumber;

    @NotNull(message = "Medication Ids must be provided ! INVALID", groups = FirstOrder.class)
    @NotEmpty(message = "List of Medication Id is empty ! BLANK", groups = SecondOrder.class)
    private List<Long> medicationIds;
}
