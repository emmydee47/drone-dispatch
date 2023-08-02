package com.technology.dronedispatch.service;


import com.technology.dronedispatch.dto.request.JobScheduleRequest;

public interface DroneJobSchedulerService {

    void scheduleDroneJob(JobScheduleRequest jobScheduleRequest);
}
