package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;

public class DateISO8601FormatException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.DateISO8601FormatException;
    public static final String EXC_ERROR_DESCRIPTION = "Not a valid date format";
    public static final String ERROR_MESSAGE = "Please provide a valid date format (i.e. 2016-07-26T17:35:57.SSSZ)";


    public DateISO8601FormatException(Class aClass, String excField) {
        super(ERROR_MESSAGE, EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass, excField);
    }

}