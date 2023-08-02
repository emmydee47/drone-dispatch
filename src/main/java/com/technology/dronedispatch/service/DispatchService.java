package com.technology.dronedispatch.service;


import com.technology.dronedispatch.dto.request.DroneLoadingRequest;
import com.technology.dronedispatch.dto.request.DroneRegisterRequest;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.Medication;
import com.technology.dronedispatch.model.entity.projection.DroneView;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DispatchService {

    @Transactional
    ResponseEntity<ResponseDto<Drone>> registerDrone(DroneRegisterRequest request);

    @Transactional
    ResponseEntity<ResponseDto<Drone>> loadDrone(DroneLoadingRequest request);

    @Transactional(readOnly = true)
    ResponseEntity<ResponseDto<Page<Medication>>> getMedicationItemsByDrone(String droneSerialNumber, int page, int pageRecord);

    @Transactional(readOnly = true)
    ResponseEntity<ResponseDto<Page<DroneView>>> getAvailableDrones(int page, int pageRecord);

    @Transactional(readOnly = true)
    ResponseEntity<ResponseDto<Integer>> getBatteryLevelByDrone(String droneSerialNumber);

    @Transactional
    ResponseDto<Optional<Drone>> createDrone(Drone drone);
}
