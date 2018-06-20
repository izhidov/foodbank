package com.inzami.fp.exception;

import com.inzami.fp.domain.User;
import com.inzami.fp.enums.ExceptionCode;

public class UserAlreadyExistException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.UserNotMatchException;
    public static final String EXC_ERROR_DESCRIPTION = "User already exists";
    public static final String ERROR_MESSAGE = "User already exists by email:`%s`";


    public UserAlreadyExistException(String email) {
        super(String.format(ERROR_MESSAGE, email), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, User.class);
    }
}
