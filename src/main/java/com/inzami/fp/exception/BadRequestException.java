package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class BadRequestException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.BadRequestException;
    public static final String EXC_ERROR_DESCRIPTION = "Cannot execute request";
    public static final String ERROR_MESSAGE = "Cannot execute request. Reason: %s";

    public BadRequestException(Class aClass, String errorMessage) {
        super(String.format(ERROR_MESSAGE, errorMessage), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
