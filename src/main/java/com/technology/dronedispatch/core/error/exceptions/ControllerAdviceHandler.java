package com.technology.dronedispatch.core.error.exceptions;


import com.technology.dronedispatch.core.error.CustomException;
import com.technology.dronedispatch.core.error.constants.ErrorMark;
import com.technology.dronedispatch.core.error.model.ErrorDetails;
import com.technology.dronedispatch.dto.response.ResponseDto;
import com.technology.dronedispatch.model.enums.ResponseCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.NestedServletException;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Optional;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ResponseDto<ErrorDetails>> handleCustomException(CustomException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(ex.getStatus().value() != 0 ? ex.getStatus().value() : HttpStatus.PRECONDITION_FAILED.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ResponseDto<ErrorDetails>> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());

    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<ResponseDto<ErrorDetails>> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(ex.getStatusCode().value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());

    }

    @ExceptionHandler(NoResultException.class)
    public final ResponseEntity<ResponseDto<ErrorDetails>> handleNoResultException(NoResultException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler({MappingException.class})
    public final ResponseEntity<ResponseDto<ErrorDetails>> handleMappingException(MappingException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, NestedServletException.class})
    public ResponseEntity<ResponseDto<ErrorDetails>> handleIllegalArgumentException(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(RequestRejectedException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleRequestRejectedException(RequestRejectedException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleStaleObjectStateException(StaleObjectStateException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<ResponseDto<ErrorDetails>> handleConcurrentModificationException(ConcurrentModificationException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(true))
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.<ErrorDetails>builder()
                .statusMessage(errorDetails.getMessage())
                .statusCode(String.valueOf(errorDetails.getCode()))
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResponseDto> processValidationError(final MethodArgumentNotValidException ex){
        ResponseDto noDataResponseDto = new ResponseDto<>();
        String returnResponseCode = "";

        String field_error = ex.getBindingResult().getAllErrors().stream().findFirst()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .toString();

        //clean field value which is in the form Optional[error_statement]
        field_error = field_error.replace("Optional[","");
        field_error = field_error.replace("]","");
        System.out.println("field error =>"+field_error);

        String returnMessage = StringUtils.substringBeforeLast(field_error,"!").trim();
        String errorMark = StringUtils.substringAfterLast(field_error,"!").trim();

        if(errorMark.equals(ErrorMark.BLANK.getErrorMark())){
            returnResponseCode = ResponseCode.blankParamCode();
        }
        if(errorMark.equals(ErrorMark.INVALID.getErrorMark())){
            returnResponseCode = ResponseCode.invalidParamCode();
        }
        if(errorMark.equals(ErrorMark.NOT_FOUND.getErrorMark())){
            returnResponseCode = ResponseCode.notFoundCode();
        }

        noDataResponseDto.setStatusCode(returnResponseCode);
        noDataResponseDto.setStatusMessage(returnMessage);

        return new ResponseEntity<>(noDataResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        ResponseDto noDataResponseDto = new ResponseDto<>();
        Optional<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations().stream().findFirst();
        String errorMessage = "";
        String returnResponseCode = "";

        if (violations.isPresent()) {
            String fieldError = violations.get().getMessage();

            String returnMessage = StringUtils.substringBeforeLast(fieldError, "!").trim();
            String errorMark = StringUtils.substringAfterLast(fieldError, "!").trim();

            if(errorMark.equals(ErrorMark.BLANK.getErrorMark())){
                returnResponseCode = ResponseCode.blankParamCode();
            }
            if(errorMark.equals(ErrorMark.INVALID.getErrorMark())){
                returnResponseCode = ResponseCode.invalidParamCode();
            }
            if(errorMark.equals(ErrorMark.NOT_FOUND.getErrorMark())){
                returnResponseCode = ResponseCode.notFoundCode();
            }

            errorMessage = returnMessage;

        } else {
            errorMessage = "Constraint Violation Exception occurred.";
        }

        noDataResponseDto.setStatusMessage(errorMessage);
        noDataResponseDto.setStatusCode(returnResponseCode);

        return new ResponseEntity<>(noDataResponseDto, HttpStatus.OK);
    }
}
