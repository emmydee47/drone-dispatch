package com.technology.dronedispatch.repository;


import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.projection.DroneView;
import com.technology.dronedispatch.model.enums.DroneState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String droneSerialNumber);

    List<Drone> findByBatteryCapacityIsNot(int batteryCapacity);

    Page<DroneView> findByStateIn(Set<DroneState> droneStates, Pageable pageable);
}
