package com.technology.dronedispatch.service.impl;


import com.technology.dronedispatch.config.ConfigConstant;
import com.technology.dronedispatch.dto.request.DroneLoadingRequest;
import com.technology.dronedispatch.dto.request.DroneRegisterRequest;
import com.technology.dronedispatch.dto.response.ApiResponse;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.mapper.DroneMapper;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.Medication;
import com.technology.dronedispatch.model.entity.projection.DroneView;
import com.technology.dronedispatch.model.enums.DroneState;
import com.technology.dronedispatch.repository.DroneRepository;
import com.technology.dronedispatch.repository.MedicationRepository;
import com.technology.dronedispatch.service.DispatchService;
import com.technology.dronedispatch.service.SequencerService;
import com.technology.dronedispatch.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.technology.dronedispatch.model.enums.DroneState.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class DispatchServiceImpl extends ApiResponse implements DispatchService {

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;
    private final SequencerService sequencerService;
    private final MedicationRepository medicationRepository;
    private final ConfigConstant configConstant;

    @Override
    public ResponseEntity<ResponseDto<Drone>> registerDrone(DroneRegisterRequest request) {
        //create drone object from mapper and persist
        Drone drone = droneMapper.toDrone(request);
        ResponseDto<Optional<Drone>> saveDroneResp = createDrone(drone);

        if(saveDroneResp.getData().isEmpty()){
            return new AccessDenied<Drone>()
                    .statusMessage(saveDroneResp.getStatusMessage())
                    .statusCode(saveDroneResp.getStatusCode())
                    .build();
        }

        return Created(saveDroneResp.getData().get())
                .statusMessage("New Drone Registered")
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto<Drone>> loadDrone(DroneLoadingRequest request) {
        //get drone by serial number
        String droneSerialNumber = request.getDroneSerialNumber();
        Optional<Drone> optionalDrone
                = droneRepository.findBySerialNumber(droneSerialNumber);

        if (optionalDrone.isEmpty()) {
            return new NotFound<Drone>()
                    .statusMessage("Drone with the Serial Number: '"
                            + droneSerialNumber + "' not found")
                    .notFoundCode()
                    .build();
        }

        Drone drone = optionalDrone.get();

        //check drone availability
        ResponseEntity<ResponseDto<Drone>> droneAvailabilityResp = checkDroneAvailability(drone);
        if (droneAvailabilityResp != null) {
            return droneAvailabilityResp;
        }

        Set<Medication> medications = new LinkedHashSet<>();

        //validate medication requests
        for (Long medicationId : request.getMedicationIds()) {
            if (medicationId <= 0) {
                return new BadRequest<Drone>()
                        .statusMessage("Invalid Medication Id: '"
                                + medicationId + "', Id must not be zero or lesser")
                        .invalidParamCode()
                        .build();
            }

            //medication id must not be duplicated
            if (Collections.frequency(request.getMedicationIds(), medicationId) > 1) {
                return new BadRequest<Drone>()
                        .statusMessage("Duplicate Medication Id: '" + medicationId + "'")
                        .duplicateParamCode()
                        .build();
            }

            Optional<Medication> optionalMedication = medicationRepository.findById(medicationId);

            if (optionalMedication.isEmpty()) {
                return new NotFound<Drone>()
                        .statusMessage("Medication with Id: '"
                                + medicationId + "' not found")
                        .notFoundCode()
                        .build();
            }

            Medication medication = optionalMedication.get();

            //check if medication is already loaded on a drone
            if (Objects.nonNull(medication.getDrone())) {
                return new AccessDenied<Drone>()
                        .statusMessage("Medication with Id: '"
                                + medicationId + "' has been loaded on a drone already")
                        .lowDroneBatteryCode()
                        .build();
            }

            medications.add(medication);
        }

        //get total loading weight of medications
        double totalLoadingWeight = medications.stream()
                .map(Medication::getWeight)
                .reduce(Double::sum).orElse(0.0);

        //check if the total loading weight is more than the drone's free weight
        if (totalLoadingWeight > drone.getFreeWeight()) {
            return new AccessDenied<Drone>()
                    .statusMessage("Total Loading Weight: "
                            + totalLoadingWeight + ", is more than the Drone's free Weight: "
                            + drone.getFreeWeight())
                    .accessDeniedCode()
                    .build();
        }

        //load the drone and update its state
        drone.addMedications(medications);
        DroneState newDroneState = totalLoadingWeight == drone.getFreeWeight() ? LOADED : LOADING;
        drone.setState(newDroneState);

        //persist the drone
        Drone loadedDrone = droneRepository.save(drone);

        //persist the medications
        medications.forEach(m->m.setDrone(loadedDrone));
        medicationRepository.saveAll(medications);

        return Ok(loadedDrone)
                .statusMessage("Drone loaded successfully")
                .build();
    }

    private ResponseEntity<ResponseDto<Drone>> checkDroneAvailability(Drone drone) {
        //check drone battery
        int droneLowBatteryPercentageThreshold = configConstant.getDroneLowBatteryPercentageThreshold();
        if (drone.getBatteryCapacity() < droneLowBatteryPercentageThreshold) {
            return new AccessDenied<Drone>()
                    .statusMessage("Drone Battery is below " + droneLowBatteryPercentageThreshold + "%")
                    .lowDroneBatteryCode()
                    .build();
        }

        //check if drone is already fully loaded
        DroneState droneState = drone.getState();
        if (droneState.equals(LOADED)) {
            return new AccessDenied<Drone>()
                    .statusMessage("The Drone is already fully loaded")
                    .maximumParameterLimitCode()
                    .build();
        }

        //check if drone has been dispatched
        Set<DroneState> dispatchedDroneStates = Set.of(DELIVERING, DELIVERED, RETURNING);
        if (dispatchedDroneStates.contains(droneState)) {
            return new AccessDenied<Drone>()
                    .statusMessage("The Drone has been dispatched")
                    .maximumParameterLimitCode()
                    .build();
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<Page<Medication>>> getMedicationItemsByDrone(String droneSerialNumber,
                                                                                   int page, int pageRecord) {
        if (StringUtils.isBlank(droneSerialNumber)) {
            return new BadRequest<Page<Medication>>()
                    .statusMessage("Drone Serial Number must be provided")
                    .notFoundCode()
                    .build();
        }

        Optional<Drone> optionalDrone
                = droneRepository.findBySerialNumber(droneSerialNumber);

        if (optionalDrone.isEmpty()) {
            return new BadRequest<Page<Medication>>()
                    .statusMessage("Drone with the Serial Number: '"
                            + droneSerialNumber + "' not found")
                    .notFoundCode()
                    .build();
        }

        List<Medication> medications
                = optionalDrone.get().getMedications().stream().toList();

        Page<Medication> medicationPage
                = PageableExecutionUtils.getPage(
                        medications, Pagination.pageable(page, pageRecord), medications::size);

        return Ok(medicationPage)
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto<Page<DroneView>>> getAvailableDrones(int page, int pageRecord) {
        Page<DroneView> availableDrones
                = droneRepository.findByStateIn(
                        Set.of(IDLE, LOADING), Pagination.pageable(page, pageRecord));

        return Ok(availableDrones)
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto<Integer>> getBatteryLevelByDrone(String droneSerialNumber) {
        if (StringUtils.isBlank(droneSerialNumber)) {
            return new BadRequest<Integer>()
                    .statusMessage("Drone Serial Number must be provided")
                    .blankParamCode()
                    .build();
        }

        Optional<Drone> optionalDrone
                = droneRepository.findBySerialNumber(droneSerialNumber);

        if (optionalDrone.isEmpty()) {
            return new NotFound<Integer>()
                    .statusMessage("Drone with the Serial Number: '"
                            + droneSerialNumber + "' not found")
                    .notFoundCode()
                    .build();
        }

        return Ok(optionalDrone.get().getBatteryCapacity())
                .build();
    }

    @Override
    public ResponseDto<Optional<Drone>> createDrone(Drone drone) {
        ResponseDto<Optional<String>> serialNoResp = sequencerService.generateSerialNumber();

        if(serialNoResp.getData().isEmpty()){
            return ResponseDto.<Optional<Drone>>builder()
                    .data(Optional.empty())
                    .statusMessage(serialNoResp.getStatusMessage())
                    .statusCode(serialNoResp.getStatusCode())
                    .build();
        }

        String serialNo = serialNoResp.getData().get();
        drone.setSerialNumber(serialNo);

        Optional<Drone> persistedDrone = Optional.of(droneRepository.save(drone));
        return ResponseDto.<Optional<Drone>>builder()
                .data(persistedDrone)
                .statusMessage(serialNoResp.getStatusMessage())
                .statusCode(serialNoResp.getStatusCode())
                .build();
    }

}
