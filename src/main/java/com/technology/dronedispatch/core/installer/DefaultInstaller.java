package com.technology.dronedispatch.core.installer;

import com.technology.dronedispatch.config.ConfigConstant;
import com.technology.dronedispatch.dto.request.JobScheduleRequest;
import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.model.entity.Medication;
import com.technology.dronedispatch.model.enums.DroneModel;
import com.technology.dronedispatch.repository.DroneRepository;
import com.technology.dronedispatch.repository.MedicationRepository;
import com.technology.dronedispatch.service.DispatchService;
import com.technology.dronedispatch.service.impl.DroneDroneJobSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultInstaller implements ApplicationListener<ContextRefreshedEvent> {
    private final DroneRepository droneRepository;
    private final DispatchService dispatchService;
    private final DroneDroneJobSchedulerService droneJobSchedulerService;
    private final MedicationRepository medicationRepository;
    private final ConfigConstant configConstant;
    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createFleetOfDrones();
        createMedicationItems();
        startDroneBatteryCapacityAudit();

        alreadySetup = true;
    }

    public void startDroneBatteryCapacityAudit() {
        JobScheduleRequest jobScheduleRequest = new JobScheduleRequest();
        jobScheduleRequest.setIntervalInSec(20);
        jobScheduleRequest.setTimeZone(TimeZone.getDefault().toZoneId());
        droneJobSchedulerService.scheduleDroneJob(jobScheduleRequest);
    }

    public void createFleetOfDrones() {
        Random rand = new Random();

        int fleetSize = configConstant.getDroneFleetSize();
        int noOfDrones = (int) droneRepository.count();
        int ensureFleetSize;

        if (noOfDrones < fleetSize) {
            ensureFleetSize = fleetSize - noOfDrones;

            for (int i = 0; i < ensureFleetSize; i++) {
                int low = 100;
                int high = 500;
                int randomWeight = rand.nextInt(high - low) + low;

                Drone drone = Drone.builder()
                        .model(DroneModel.getRandomModel())
                        .weightLimit(randomWeight)
                        .build();
                dispatchService.createDrone(drone);
            }
        }
    }

    public void createMedicationItems() {
        List<Medication> medications = new ArrayList<>();
        Random rand = new Random();

        int expectedCount = configConstant.getSampleMedicationCount();
        int noOfMedications = (int) medicationRepository.count();
        int ensureMedicationCount;

        if (noOfMedications < expectedCount) {
            ensureMedicationCount = expectedCount - noOfMedications;

            for (int i = 1; i <= ensureMedicationCount; i++) {
                int low = 50;
                int high = 300;
                int randomWeight = rand.nextInt(high - low) + low;

                int randomCode = rand.nextInt(5000 - 1000) + 1000;

                Medication medication = new Medication();
                medication.setName("medication" + i);
                medication.setWeight(randomWeight);
                medication.setCode(String.valueOf(randomCode));
                medications.add(medication);
            }

            medicationRepository.saveAll(medications);
        }
    }


}
