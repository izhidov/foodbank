package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class CreateNewEntityException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.CreateNewEntityException;
    public static final String EXC_ERROR_DESCRIPTION = "Cannot create new entity";
    public static final String ERROR_MESSAGE = "Cannot create new entity. Reason: %s";

    public CreateNewEntityException(Class aClass, String errorMessage) {
        super(String.format(ERROR_MESSAGE, errorMessage), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }
}
