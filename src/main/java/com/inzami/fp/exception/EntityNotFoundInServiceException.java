package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class EntityNotFoundInServiceException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.EntityNotFoundInServiceException;
    public static final String EXC_ERROR_DESCRIPTION = "Entity not found";
    public static final String ERROR_MESSAGE1 = "Entity not found";
    public static final String ERROR_MESSAGE2 = "Entity not found by id: %s";
    public static final String ERROR_MESSAGE3 = "Entity not found by %s: %s";

    public EntityNotFoundInServiceException(Class aClass) {
        super(ERROR_MESSAGE1, EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }

    public EntityNotFoundInServiceException(Class aClass, Long id) {
        super(String.format(ERROR_MESSAGE2, id), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }

    public EntityNotFoundInServiceException(Class aClass, String id) {
        super(String.format(ERROR_MESSAGE2, id), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }

    public EntityNotFoundInServiceException(Class aClass, String id, String excField) {
        super(String.format(ERROR_MESSAGE3, excField, id), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, excField);
    }

}
