package com.technology.dronedispatch.model.entity.projection;

import java.sql.Timestamp;

/**
 * A Projection for the {@link com.technology.dronedispatch.model.entity.BaseEntity} entity
 */
public interface BaseEntityView {
    Long getId();

    Timestamp getCreatedAt();

    Timestamp getUpdatedAt();

    Integer getVersion();
}