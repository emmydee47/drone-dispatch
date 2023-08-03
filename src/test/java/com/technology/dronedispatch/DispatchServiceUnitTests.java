package com.technology.dronedispatch;

import com.technology.dronedispatch.config.ConfigConstant;
import com.technology.dronedispatch.dto.request.DroneLoadingRequest;
import com.technology.dronedispatch.dto.request.DroneRegisterRequest;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.mapper.DroneMapper;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.Medication;
import com.technology.dronedispatch.model.enums.DroneModel;
import com.technology.dronedispatch.model.enums.DroneState;
import com.technology.dronedispatch.model.enums.ResponseCode;
import com.technology.dronedispatch.repository.DroneRepository;
import com.technology.dronedispatch.repository.MedicationRepository;
import com.technology.dronedispatch.service.SequencerService;
import com.technology.dronedispatch.service.impl.DispatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class DispatchServiceUnitTests {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private SequencerService sequencerService;

    @Mock
    private DroneMapper droneMapper;

    @Mock
    private ConfigConstant configConstant;

    @InjectMocks
    private DispatchServiceImpl dispatchService;

    @Test
    public void testRegisterDrone_Success() {
        DroneModel randomDroneModel = DroneModel.getRandomModel();
        Double weightLimit = 300.00;
        DroneRegisterRequest request = DroneRegisterRequest.builder()
                .model(randomDroneModel.getModel())
                .weightLimit(weightLimit)
                .build();

        Drone drone = Drone.builder()
                .model(randomDroneModel)
                .weightLimit(weightLimit)
                .build();

        when(droneMapper.toDrone(request)).thenReturn(drone);
        when(sequencerService.generateSerialNumber()).thenReturn(
                ResponseDto.<Optional<String>>builder()
                        .data(Optional.of("001"))
                        .build());
        when(droneRepository.save(drone)).thenReturn(drone);

        // Act
        var response = dispatchService.registerDrone(request);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(randomDroneModel, response.getBody().getData().getModel());
        assertEquals(weightLimit, Double.valueOf(response.getBody().getData().getWeightLimit()));
        assertEquals(DroneState.IDLE, response.getBody().getData().getState());

        verify(droneRepository, times(1)).save(any());
    }

    @Test
    public void testLoadDrone_Success() {
        String droneSerialNumber = "DRN123";
        int droneBatteryCapacity = 80;
        double droneWeightLimit = 500.00;
        Set<Long> medicationIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .batteryCapacity(droneBatteryCapacity)
                .weightLimit(droneWeightLimit)
                .state(DroneState.IDLE)
                .build();

        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));

        List<Medication> medications = Arrays.asList(
                Medication.builder().id(1L).weight(100).build(),
                Medication.builder().id(2L).weight(150).build(),
                Medication.builder().id(3L).weight(200).build()
        );

        int i = 0;
        for (Long medicationId : medicationIds) {
            when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medications.get(i)));
            i++;
        }

        when(medicationRepository.saveAll(Set.copyOf(medications))).thenReturn(medications);
        when(droneRepository.save(drone)).thenReturn(drone);

        DroneLoadingRequest loadingRequest = new DroneLoadingRequest();
        loadingRequest.setDroneSerialNumber(droneSerialNumber);
        loadingRequest.setMedicationIds(new ArrayList<>(medicationIds));

        // Act
        var response = dispatchService.loadDrone(loadingRequest);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(droneSerialNumber, response.getBody().getData().getSerialNumber());
        assertEquals(DroneState.LOADING, response.getBody().getData().getState());

        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
        verify(medicationRepository, times(medicationIds.size())).findById(anyLong());
        verify(medicationRepository, times(1)).saveAll(Set.copyOf(medications));
        verify(droneRepository, times(1)).save(any());
    }

    @Test
    public void testGetMedicationItemsByDrone_Success() {
        String droneSerialNumber = "DRN123";

        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .weightLimit(300.00)
                .build();

        Medication medication1 = Medication.builder()
                .id(1L)
                .name("Medication 1")
                .weight(100)
                .drone(drone)
                .build();

        Medication medication2 = Medication.builder()
                .id(2L)
                .name("Medication 2")
                .weight(150)
                .drone(drone)
                .build();

        List<Medication> medications = Arrays.asList(medication1, medication2);
        drone.addMedications(medications);

        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));

        // Act
        var response
                = dispatchService.getMedicationItemsByDrone(droneSerialNumber, 0, 20);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(medications, response.getBody().getData().stream().toList());
        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
    }

    @Test
    public void testLoadDrone_MedicationIdsWithDuplicates() {
        String droneSerialNumber = "DRN123";

        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .batteryCapacity(100)
                .weightLimit(500)
                .state(DroneState.IDLE)
                .build();

        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));
        when(configConstant.getDroneLowBatteryPercentageThreshold()).thenReturn(25);

        DroneLoadingRequest loadingRequest = DroneLoadingRequest.builder()
                .medicationIds(Arrays.asList(2L, 2L))
                .droneSerialNumber(droneSerialNumber)
                .build();

        // Act
        var response = dispatchService.loadDrone(loadingRequest);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(ResponseCode.duplicateParamCode(), response.getBody().getStatusCode());
        assertEquals("Duplicate Medication Id: '2'", response.getBody().getStatusMessage());

        verify(medicationRepository, never()).findById(anyLong());
        verify(medicationRepository, never()).saveAll(any());
        verify(droneRepository, never()).save(any());
    }

    @Test
    public void testLoadDrone_WeightExceedsDroneLimit() {
        String droneSerialNumber = "DRN123";
        Set<Long> medicationIds = new HashSet<>(Arrays.asList(1L, 2L));

        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .batteryCapacity(80)
                .weightLimit(200) // Drone's weight limit is 200
                .state(DroneState.IDLE)
                .build();

        Medication medication1 = Medication.builder()
                .id(1L)
                .name("Medication 1")
                .weight(150)
                .drone(null)
                .build();

        Medication medication2 = Medication.builder()
                .id(2L)
                .name("Medication 2")
                .weight(100)
                .drone(null)
                .build();

        List<Medication> medications = Arrays.asList(medication1, medication2);

        int i = 0;
        for (Long medicationId : medicationIds) {
            when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medications.get(i)));
            i++;
        }

        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));

        // Act
        DroneLoadingRequest loadingRequest = new DroneLoadingRequest();
        loadingRequest.setDroneSerialNumber(droneSerialNumber);
        loadingRequest.setMedicationIds(new ArrayList<>(medicationIds));
        var response = dispatchService.loadDrone(loadingRequest);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(ResponseCode.accessDeniedCode(), response.getBody().getStatusCode());

        double totalLoadingWeight = medications.stream()
                .map(Medication::getWeight)
                .reduce(Double::sum).orElse(0.0);

        assertEquals("Total Loading Weight: "
                + totalLoadingWeight + ", is more than the Drone's free Weight: "
                + drone.getFreeWeight(), response.getBody().getStatusMessage());

        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
        verify(medicationRepository, times(medicationIds.size())).findById(anyLong());
        verify(medicationRepository, never()).saveAll(any());
        verify(droneRepository, never()).save(any());
    }

    @Test
    public void testLoadDrone_LowBattery() {
        String droneSerialNumber = "DRN123";

        int droneLowBatteryCapacityThreshold = 25;

        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .batteryCapacity(10) // Battery capacity is below the threshold (25%)
                .weightLimit(500)
                .state(DroneState.IDLE)
                .build();

        DroneLoadingRequest loadingRequest = DroneLoadingRequest.builder()
                .medicationIds(Collections.singletonList(1L))
                .droneSerialNumber(droneSerialNumber)
                .build();

        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));
        when(configConstant.getDroneLowBatteryPercentageThreshold()).thenReturn(droneLowBatteryCapacityThreshold);

        // Act
        var response = dispatchService.loadDrone(loadingRequest);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(ResponseCode.lowDroneBatteryCode(), response.getBody().getStatusCode());
        assertEquals("Drone Battery is below "
                + droneLowBatteryCapacityThreshold + "%", response.getBody().getStatusMessage());

        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
        verify(medicationRepository, never()).findById(any());
        verify(droneRepository, never()).save(any());
    }

    @Test
    public void testGetBatteryLevelByDrone_DroneNotFound() {
        String droneSerialNumber = "DRN123";
        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.empty());

        // Act
        var response = dispatchService.getBatteryLevelByDrone(droneSerialNumber);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Drone with the Serial Number: 'DRN123' not found", response.getBody().getStatusMessage());
        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
    }

    @Test
    public void testGetBatteryLevelByDrone_DroneFound() {
        String droneSerialNumber = "DRN123";
        Integer batteryCapacity = 75;
        Drone drone = Drone.builder()
                .serialNumber(droneSerialNumber)
                .batteryCapacity(batteryCapacity)
                .build();
        when(droneRepository.findBySerialNumber(droneSerialNumber)).thenReturn(Optional.of(drone));

        // Act
        ResponseEntity<ResponseDto<Integer>> response = dispatchService.getBatteryLevelByDrone(droneSerialNumber);
        log.info(response.getBody().getStatusMessage());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(batteryCapacity, response.getBody().getData());
        verify(droneRepository, times(1)).findBySerialNumber(droneSerialNumber);
    }
}
