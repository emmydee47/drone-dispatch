package com.technology.dronedispatch.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "sequencer")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Sequencer extends BaseEntity<Long> implements Serializable {

    @Column(name = "sequence", nullable = false, unique = true)
    @Builder.Default
    private int sequence = 0;

    public void incrementSequence(){
        this.sequence++;
    }

    public void decrementSequence(){
        this.sequence--;
    }

    public String getSequence(){
        return String.format(Locale.US, "%03d", this.sequence);
    }
}
