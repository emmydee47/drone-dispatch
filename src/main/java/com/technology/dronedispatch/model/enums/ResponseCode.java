package com.technology.dronedispatch.model.enums;

public enum ResponseCode {

    SUCCESSFUL("00", "Successful"),
    BLANK_PARAMETER_ERROR("01", "Blank parameter"),
    INVALID_PARAMETER_ERROR("02", "Invalid parameter"),
    EXISTING_PARAMETER_ERROR("03", "Existing parameter"),
    DUPLICATE_PARAMETER_ERROR("04", "Duplicate parameter"),
    PARAMETER_NOT_FOUND("08", "Parameter Not Found"),
    MAXIMUM_PARAMETER_LIMIT("09", "Maximum parameter Limit"),
    ACCESS_DENIED("11", "Access Denied"),
    LOW_DRONE_BATTERY_MESSAGE("12", "Drone Battery is below 25%"),
    GENERAL_ERROR_MESSAGE("12", "Operation processing failed");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    //CODES
    public static String successCode() {
        return SUCCESSFUL.getCode();
    }

    public static String blankParamCode() {
        return BLANK_PARAMETER_ERROR.getCode();
    }

    public static String invalidParamCode() {
        return INVALID_PARAMETER_ERROR.getCode();
    }

    public static String existingParamCode() {
        return EXISTING_PARAMETER_ERROR.getCode();
    }

    public static String duplicateParamCode() {
        return DUPLICATE_PARAMETER_ERROR.getCode();
    }

    public static String notFoundCode() {
        return PARAMETER_NOT_FOUND.getCode();
    }

    public static String accessDeniedCode() {
        return ACCESS_DENIED.getCode();
    }

    public static String maximumParameterLimitCode() {
        return MAXIMUM_PARAMETER_LIMIT.getCode();
    }

    public static String lowDroneBatteryCode() {
        return LOW_DRONE_BATTERY_MESSAGE.getCode();
    }

    public static String generalErrorCode() {
        return GENERAL_ERROR_MESSAGE.getCode();
    }


    //MESSAGES
    public static String successMessage() {
        return SUCCESSFUL.getMessage();
    }

    public static String blankParamMessage() {
        return BLANK_PARAMETER_ERROR.getMessage();
    }

    public static String invalidParamMessage() {
        return INVALID_PARAMETER_ERROR.getMessage();
    }

    public static String existingParamMessage() {
        return EXISTING_PARAMETER_ERROR.getMessage();
    }

    public static String duplicateParamMessage() {
        return DUPLICATE_PARAMETER_ERROR.getMessage();
    }

    public static String notFoundMessage() {
        return PARAMETER_NOT_FOUND.getMessage();
    }

    public static String accessDeniedMessage() {
        return ACCESS_DENIED.getMessage();
    }

    public static String maximumParameterLimitMessage() {
        return MAXIMUM_PARAMETER_LIMIT.getMessage();
    }

    public static String lowDroneBatteryMessage() {
        return LOW_DRONE_BATTERY_MESSAGE.getMessage();
    }

    public static String generalErrorMessage() {
        return GENERAL_ERROR_MESSAGE.getMessage();
    }
}

