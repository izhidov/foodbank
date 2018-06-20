package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class PropertyNotFoundException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.PropertyNotFoundException;
    public static final String EXC_ERROR_DESCRIPTION = "Property not found in database";
    public static final String ERROR_MESSAGE = "Property not found by name: %s";

    public PropertyNotFoundException(Class aClass, String excField) {
        super(String.format(ERROR_MESSAGE, excField), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, excField);
    }
}
