package com.technology.dronedispatch.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technology.dronedispatch.model.enums.DroneModel;
import com.technology.dronedispatch.model.enums.DroneState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.technology.dronedispatch.model.enums.DroneState.IDLE;

@Entity
@Table(name = "drone")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Audited(withModifiedFlag = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drone extends BaseEntity<Long> implements Serializable {

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    private DroneModel model;

    //max 500gr
    @Column(name = "weight_limit")
    private double weightLimit;

    @Column(name = "battery_percentage")
    @Builder.Default
    private int batteryCapacity = 100;

    @Column(name = "state")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DroneState state = IDLE;

    @Setter(AccessLevel.NONE)
    @Column(name = "supports_camera_type_device_load")
    @Builder.Default
    private boolean supportsCameraTypeDeviceLoad = false;

    @OneToMany(mappedBy = "drone")
    @Builder.Default
    private Set<Medication> medications = new LinkedHashSet<>();

    public void addMedications(Collection<Medication> medications){
        this.medications.addAll(medications);
    }

    public double getFreeWeight() {
        double totalMedicationWeight = medications.stream()
                .map(Medication::getWeight)
                .reduce(Double::sum).orElse(0.0);

        return weightLimit - totalMedicationWeight;
    }

    public void depleteBatteryCapacity() {
        if (batteryCapacity > 0) {
            setBatteryCapacity(batteryCapacity-1);
        }
    }


}
