package com.decathlon.alert.mechanism.system.exception;

import com.decathlon.alert.mechanism.system.constants.CodeConstants;
import lombok.Value;

@Value
public class AlertManagementException extends RuntimeException {

    CodeConstants.Code errorCode;

    public AlertManagementException(CodeConstants.Code errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
