package com.technology.dronedispatch.model.entity.projection;


import com.technology.dronedispatch.model.enums.DroneModel;
import com.technology.dronedispatch.model.enums.DroneState;

import java.util.Set;

/**
 * A Projection for the {@link com.technology.dronedispatch.model.entity.Drone} entity
 */
public interface DroneView extends BaseEntityView {

    String getSerialNumber();

    DroneModel getModel();

    double getWeightLimit();

    int getBatteryCapacity();

    DroneState getState();

    boolean isSupportsCameraTypeDeviceLoad();

    Set<MedicationView> getMedications();

    default double getFreeWeight() {
        double totalMedicationWeight = getMedications().stream()
                .map(MedicationView::getWeight)
                .reduce(Double::sum).orElse(0.0);

        return getWeightLimit() - totalMedicationWeight;
    }
}