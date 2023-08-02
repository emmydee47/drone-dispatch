package com.technology.dronedispatch.job;

import com.technology.dronedispatch.model.entity.Drone;
import com.technology.dronedispatch.repository.DroneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DroneJob extends QuartzJobBean {
    private final DroneRepository droneRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
        simulateRealTimeDroneBatteryDepletion();
    }

    //simulates drone battery depletion of 1% every 20sec
    @Transactional
    private void simulateRealTimeDroneBatteryDepletion() {
        //find drones with battery capacity not at zero
        List<Drone> drones = droneRepository.findByBatteryCapacityIsNot(0);

        if (!drones.isEmpty()) {
            log.info("Depleting drone batteries by 1% (every 20 seconds)");

            List<Drone> updatedDrones = drones.stream()
                    .peek(Drone::depleteBatteryCapacity).toList();

            droneRepository.saveAll(updatedDrones);
        }
    }

}
