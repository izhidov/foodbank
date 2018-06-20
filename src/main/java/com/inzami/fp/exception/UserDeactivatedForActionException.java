package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;
import com.inzami.fp.domain.User;

public class UserDeactivatedForActionException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.UserDeactivatedForActionException;
    public static final String EXC_ERROR_DESCRIPTION = "User deactivated";

    public UserDeactivatedForActionException(String message) {
        super(message, EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, User.class, "User.deactivated");
    }

}
