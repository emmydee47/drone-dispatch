package com.technology.dronedispatch.service.impl;

import com.technology.dronedispatch.dto.request.JobScheduleRequest;
import com.technology.dronedispatch.job.DroneJob;
import com.technology.dronedispatch.service.DroneJobSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service("droneJobScheduler")
public class DroneDroneJobSchedulerService implements DroneJobSchedulerService {
    private final Scheduler scheduler;

    @Override
    public void scheduleDroneJob(JobScheduleRequest jobScheduleRequest) {
        try {
            JobDetail jobDetail = buildJobDetail();
            Trigger trigger = buildJobTrigger(jobDetail, jobScheduleRequest.getIntervalInSec());
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job Scheduled Successfully!");

        } catch (SchedulerException ex) {
            log.error("Error scheduling job", ex);
        }
    }

    private JobDetail buildJobDetail() {
        return JobBuilder.newJob(DroneJob.class)
                .withIdentity(UUID.randomUUID().toString(), "drone-jobs")
                .withDescription("Send Drone Job")
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, int intervalInSec) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "drone-job-triggers")
                .withDescription("Send Drone Job Trigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withMisfireHandlingInstructionFireNow()
                        .withIntervalInSeconds(intervalInSec)
                        .repeatForever())
                .build();
    }

}
