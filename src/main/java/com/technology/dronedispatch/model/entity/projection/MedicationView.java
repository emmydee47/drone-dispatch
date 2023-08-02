package com.technology.dronedispatch.model.entity.projection;

/**
 * A Projection for the {@link com.technology.dronedispatch.model.entity.Medication} entity
 */
public interface MedicationView extends LoadView {

    String getName();

    double getWeight();

    String getCode();

    String getPictureUrl();
}