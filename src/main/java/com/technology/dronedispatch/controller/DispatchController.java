package com.technology.dronedispatch.controller;

import com.technology.dronedispatch.dto.request.DroneLoadingRequest;
import com.technology.dronedispatch.dto.request.DroneRegisterRequest;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.Medication;
import com.technology.dronedispatch.model.entity.projection.DroneView;
import com.technology.dronedispatch.service.DispatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/dispatch")
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
public class DispatchController {

    private final DispatchService dispatchService;

    @PostMapping(path = "/register-drone", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Drone>> registerDrone(@Valid
                                                            @RequestBody
                                                            DroneRegisterRequest droneRegisterRequest) {
        return dispatchService.registerDrone(droneRegisterRequest);
    }

    @PutMapping(path = "/load-drone", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Drone>> loadDrone(@Valid
                                                        @RequestBody
                                                        DroneLoadingRequest droneLoadingRequest) {
        return dispatchService.loadDrone(droneLoadingRequest);
    }

    @GetMapping(path = "/get-medication-items-by-drone", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto<Page<Medication>>> getMedicationItemsByDrone(@RequestParam(name = "droneSerialNumber")
                                                                            String droneSerialNumber,
                                                                            @RequestParam(name = "page", defaultValue = "0")
                                                                            int page,
                                                                            @RequestParam(name = "pageRecord", defaultValue = "20")
                                                                            int pageRecord) {
        return dispatchService.getMedicationItemsByDrone(droneSerialNumber, page, pageRecord);
    }

    @GetMapping(path = "/get-available-drones", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto<Page<DroneView>>> getAvailableDrones(@RequestParam(name = "page", defaultValue = "0")
                                                                    int page,
                                                                    @RequestParam(name = "pageRecord", defaultValue = "20")
                                                                    int pageRecord) {
        return dispatchService.getAvailableDrones(page, pageRecord);
    }

    @GetMapping(path = "/get-battery-level-by-drone", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto<Integer>> getBatteryLevelByDrone(@RequestParam(name = "droneSerialNumber")
                                                                String droneSerialNumber) {
        return dispatchService.getBatteryLevelByDrone(droneSerialNumber);
    }
}
