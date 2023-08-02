package com.technology.dronedispatch.repository;


import com.technology.dronedispatch.model.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String droneSerialNumber);

    List<Drone> findByBatteryCapacityIsNot(int batteryCapacity);

}
