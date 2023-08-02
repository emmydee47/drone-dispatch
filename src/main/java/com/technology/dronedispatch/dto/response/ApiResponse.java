package com.technology.dronedispatch.dto.response;


import com.technology.dronedispatch.model.enums.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static <T> Created<T> Created(T data) {
        return new Created<>(data);
    }

    public static <T> Ok<T> Ok(T data) {
        return new Ok<>(data);
    }

    public static final class BadRequest<T> implements ApiResponseCode<BadRequest<T>> {
        private T data = null;
        private String statusCode = ResponseCode.generalErrorCode();
        private String statusMessage = ResponseCode.generalErrorMessage();

        public BadRequest(T data) {
            this.data = data;
        }

        public BadRequest() {}

        public BadRequest<T> statusCode(final String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public BadRequest<T> statusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {

            ResponseDto<T> responseDto = new ResponseDto<>();

            responseDto.setData(data);
            responseDto.setStatusCode(statusCode);
            responseDto.setStatusMessage(statusMessage);

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        @Override
        public BadRequest<T> successCode() {
            this.statusCode = ResponseCode.successCode();
            return this;
        }

        @Override
        public BadRequest<T> blankParamCode() {
            this.statusCode = ResponseCode.blankParamCode();
            return this;
        }

        @Override
        public BadRequest<T> invalidParamCode() {
            this.statusCode = ResponseCode.invalidParamCode();
            return this;
        }

        @Override
        public BadRequest<T> existingParamCode() {
            this.statusCode = ResponseCode.existingParamCode();
            return this;
        }

        @Override
        public BadRequest<T> duplicateParamCode() {
            this.statusCode = ResponseCode.duplicateParamCode();
            return this;
        }

        @Override
        public BadRequest<T> notFoundCode() {
            this.statusCode = ResponseCode.notFoundCode();
            return this;
        }

        @Override
        public BadRequest<T> accessDeniedCode() {
            this.statusCode = ResponseCode.accessDeniedCode();
            return this;
        }

        @Override
        public BadRequest<T> maximumParameterLimitCode() {
            this.statusCode = ResponseCode.maximumParameterLimitCode();
            return this;
        }

        @Override
        public BadRequest<T> generalErrorCode() {
            this.statusCode = ResponseCode.generalErrorCode();
            return this;
        }

        @Override
        public BadRequest<T> lowDroneBatteryCode() {
            this.statusCode = ResponseCode.lowDroneBatteryCode();
            return this;
        }
    }

    public static final class NotFound<T> implements ApiResponseCode<NotFound<T>> {
        private T data = null;
        private String statusCode = ResponseCode.notFoundCode();
        private String statusMessage = ResponseCode.notFoundMessage();

        public NotFound(T data) {
            this.data = data;
        }

        public NotFound() {}

        public NotFound<T> statusCode(final String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public NotFound<T> statusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {
            ResponseDto<T> responseDto = new ResponseDto<>();

            responseDto.setData(data);
            responseDto.setStatusCode(statusCode);
            responseDto.setStatusMessage(statusMessage);

            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }

        @Override
        public NotFound<T> successCode() {
            this.statusCode = ResponseCode.successCode();
            return this;
        }

        @Override
        public NotFound<T> blankParamCode() {
            this.statusCode = ResponseCode.blankParamCode();
            return this;
        }

        @Override
        public NotFound<T> invalidParamCode() {
            this.statusCode = ResponseCode.invalidParamCode();
            return this;
        }

        @Override
        public NotFound<T> existingParamCode() {
            this.statusCode = ResponseCode.existingParamCode();
            return this;
        }

        @Override
        public NotFound<T> duplicateParamCode() {
            this.statusCode = ResponseCode.duplicateParamCode();
            return this;
        }

        @Override
        public NotFound<T> notFoundCode() {
            this.statusCode = ResponseCode.notFoundCode();
            return this;
        }

        @Override
        public NotFound<T> accessDeniedCode() {
            this.statusCode = ResponseCode.accessDeniedCode();
            return this;
        }

        @Override
        public NotFound<T> maximumParameterLimitCode() {
            this.statusCode = ResponseCode.maximumParameterLimitCode();
            return this;
        }

        @Override
        public NotFound<T> generalErrorCode() {
            this.statusCode = ResponseCode.generalErrorCode();
            return this;
        }

        @Override
        public NotFound<T> lowDroneBatteryCode() {
            this.statusCode = ResponseCode.lowDroneBatteryCode();
            return this;
        }
    }

    public static final class Ok<T> implements ApiResponseCode<Ok<T>> {

        private T data = null;
        private String statusCode = ResponseCode.successCode();
        private String statusMessage = ResponseCode.successMessage();

        public Ok(T data) {
            this.data = data;
        }

        public Ok() {}

        public Ok<T> statusCode(final String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Ok<T> statusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {
            ResponseDto<T> responseDto = new ResponseDto<>();

            responseDto.setData(data);
            responseDto.setStatusCode(statusCode);
            responseDto.setStatusMessage(statusMessage);

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

        @Override
        public Ok<T> successCode() {
            this.statusCode = ResponseCode.successCode();
            return this;
        }

        @Override
        public Ok<T> blankParamCode() {
            this.statusCode = ResponseCode.blankParamCode();
            return this;
        }

        @Override
        public Ok<T> invalidParamCode() {
            this.statusCode = ResponseCode.invalidParamCode();
            return this;
        }

        @Override
        public Ok<T> existingParamCode() {
            this.statusCode = ResponseCode.existingParamCode();
            return this;
        }

        @Override
        public Ok<T> duplicateParamCode() {
            this.statusCode = ResponseCode.duplicateParamCode();
            return this;
        }

        @Override
        public Ok<T> notFoundCode() {
            this.statusCode = ResponseCode.notFoundCode();
            return this;
        }

        @Override
        public Ok<T> accessDeniedCode() {
            this.statusCode = ResponseCode.accessDeniedCode();
            return this;
        }

        @Override
        public Ok<T> maximumParameterLimitCode() {
            this.statusCode = ResponseCode.maximumParameterLimitCode();
            return this;
        }

        @Override
        public Ok<T> generalErrorCode() {
            this.statusCode = ResponseCode.generalErrorCode();
            return this;
        }

        @Override
        public Ok<T> lowDroneBatteryCode() {
            this.statusCode = ResponseCode.lowDroneBatteryCode();
            return this;
        }
    }

    public static final class Created<T> implements ApiResponseCode<Created<T>> {
        private T data = null;
        private String statusCode = ResponseCode.successCode();
        private String statusMessage = ResponseCode.successMessage();

        public Created(T data) {
            this.data = data;
        }

        public Created() {}

        public Created<T> statusCode(final String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Created<T> statusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {

            ResponseDto<T> responseDto = new ResponseDto<>();

            responseDto.setData(data);
            responseDto.setStatusCode(statusCode);
            responseDto.setStatusMessage(statusMessage);

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }

        @Override
        public Created<T> successCode() {
            this.statusCode = ResponseCode.successCode();
            return this;
        }

        @Override
        public Created<T> blankParamCode() {
            this.statusCode = ResponseCode.blankParamCode();
            return this;
        }

        @Override
        public Created<T> invalidParamCode() {
            this.statusCode = ResponseCode.invalidParamCode();
            return this;
        }

        @Override
        public Created<T> existingParamCode() {
            this.statusCode = ResponseCode.existingParamCode();
            return this;
        }

        @Override
        public Created<T> duplicateParamCode() {
            this.statusCode = ResponseCode.duplicateParamCode();
            return this;
        }

        @Override
        public Created<T> notFoundCode() {
            this.statusCode = ResponseCode.notFoundCode();
            return this;
        }

        @Override
        public Created<T> accessDeniedCode() {
            this.statusCode = ResponseCode.accessDeniedCode();
            return this;
        }

        @Override
        public Created<T> maximumParameterLimitCode() {
            this.statusCode = ResponseCode.maximumParameterLimitCode();
            return this;
        }

        @Override
        public Created<T> generalErrorCode() {
            this.statusCode = ResponseCode.generalErrorCode();
            return this;
        }

        @Override
        public Created<T> lowDroneBatteryCode() {
            this.statusCode = ResponseCode.lowDroneBatteryCode();
            return this;
        }
    }


    public static final class AccessDenied<T> implements ApiResponseCode<AccessDenied<T>> {

        private T data = null;
        private String statusCode = ResponseCode.accessDeniedCode();
        private String statusMessage = "You don't have permission to access this resource";

        public AccessDenied(T data) {
            this.data = data;
        }

        public AccessDenied() {}

        public AccessDenied<T> statusCode(final String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public AccessDenied<T> statusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseEntity<ResponseDto<T>> build() {
            ResponseDto<T> responseDto = new ResponseDto<>();

            responseDto.setData(data);
            responseDto.setStatusCode(statusCode);
            responseDto.setStatusMessage(statusMessage);

            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        }

        @Override
        public AccessDenied<T> successCode() {
            this.statusCode = ResponseCode.successCode();
            return this;
        }

        @Override
        public AccessDenied<T> blankParamCode() {
            this.statusCode = ResponseCode.blankParamCode();
            return this;
        }

        @Override
        public AccessDenied<T> invalidParamCode() {
            this.statusCode = ResponseCode.invalidParamCode();
            return this;
        }

        @Override
        public AccessDenied<T> existingParamCode() {
            this.statusCode = ResponseCode.existingParamCode();
            return this;
        }

        @Override
        public AccessDenied<T> duplicateParamCode() {
            this.statusCode = ResponseCode.duplicateParamCode();
            return this;
        }

        @Override
        public AccessDenied<T> notFoundCode() {
            this.statusCode = ResponseCode.notFoundCode();
            return this;
        }

        @Override
        public AccessDenied<T> accessDeniedCode() {
            this.statusCode = ResponseCode.accessDeniedCode();
            return this;
        }

        @Override
        public AccessDenied<T> maximumParameterLimitCode() {
            this.statusCode = ResponseCode.maximumParameterLimitCode();
            return this;
        }

        @Override
        public AccessDenied<T> generalErrorCode() {
            this.statusCode = ResponseCode.generalErrorCode();
            return this;
        }

        @Override
        public AccessDenied<T> lowDroneBatteryCode() {
            this.statusCode = ResponseCode.lowDroneBatteryCode();
            return this;
        }
    }

}
