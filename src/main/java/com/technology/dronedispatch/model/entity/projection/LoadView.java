package com.technology.dronedispatch.model.entity.projection;


import com.technology.dronedispatch.model.enums.DeviceType;
import com.technology.dronedispatch.model.enums.LoadType;

/**
 * A Projection for the {@link com.technology.dronedispatch.model.entity.Load} entity
 */
public interface LoadView extends BaseEntityView {

    LoadType getLoadType();

    DeviceType getDeviceType();
}