package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class EntityAlreadyExistsException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.EntityAlreadyExistsException;
    public static final String EXC_ERROR_DESCRIPTION = "Entity already exists";
    public static final String ERROR_MESSAGE = "Entity already exists in the database";

    public EntityAlreadyExistsException(Class aClass) {
        super(ERROR_MESSAGE, EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, null);
    }

}
