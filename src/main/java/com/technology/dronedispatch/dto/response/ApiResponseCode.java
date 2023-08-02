package com.technology.dronedispatch.dto.response;

public interface ApiResponseCode<T>{

    //RESPONSE CODES
    T successCode();

    T blankParamCode();

    T invalidParamCode();

    T existingParamCode();

    T duplicateParamCode();

    T notFoundCode();

    T accessDeniedCode();

    T maximumParameterLimitCode();

    T generalErrorCode();

    T lowDroneBatteryCode();
}
