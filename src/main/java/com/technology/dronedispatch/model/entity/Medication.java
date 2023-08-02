package com.technology.dronedispatch.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "medication")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Audited(withModifiedFlag = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medication extends Load<Long> implements Serializable {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "code")
    private String code;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "drone_fk")
    @JsonIgnore
    private Drone drone;
}
