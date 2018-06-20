package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;
import com.inzami.fp.domain.User;

import java.util.Arrays;

public class WrongUserRoleForActionException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.WrongUserRoleForActionException;
    public static final String EXC_ERROR_DESCRIPTION = "Wrong user role";
    public static final String ERROR_MESSAGE = "Wrong user role: %s(%s), expected: %s";

    public WrongUserRoleForActionException(String[] actual, String[] expected, String email) {
        super(String.format(ERROR_MESSAGE, Arrays.toString(actual), email, Arrays.toString(expected)), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, User.class, null);
    }
}
