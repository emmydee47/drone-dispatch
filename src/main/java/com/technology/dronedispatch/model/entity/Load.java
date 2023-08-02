package com.technology.dronedispatch.model.entity;

import com.technology.dronedispatch.model.enums.DeviceType;
import com.technology.dronedispatch.model.enums.LoadType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static com.technology.dronedispatch.model.enums.LoadType.SMALL_LOAD;


@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public abstract class Load<U> extends BaseEntity<U> {

    @Column(name = "load_type")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LoadType loadType = SMALL_LOAD;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
}
