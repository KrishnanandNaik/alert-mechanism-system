package com.decathlon.alert.mechanism.system.exception;

import com.decathlon.alert.mechanism.system.constants.CodeConstants;
import com.decathlon.alert.mechanism.system.controller.response.ResponseWrapper;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
@Order
public class CustomExceptionHandler {

    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<ResponseWrapper> handleHttpMessageNotReadableException(RuntimeException ex) {
        log.error("HTTP message not readable", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper(CodeConstants.PARAM_ILLEGAL, null));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException ex", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper(CodeConstants.BAD_REQUEST, "Invalid request!!"));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException ex", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper(CodeConstants.PARAM_ILLEGAL, "Invalid request!!"));
    }

    @ExceptionHandler(value = AlertManagementException.class)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> handleAlertManagementException(AlertManagementException ex) {
        log.error("AlertManagementException ex", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseWrapper(ex.getErrorCode(), null));
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResponseEntity<ResponseWrapper> handleThrowableException(Throwable ex) {
        log.error("Throwable ex", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper(CodeConstants.SYSTEM_ERROR, null));
    }

}
