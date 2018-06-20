package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;
import com.inzami.fp.domain.User;

public class UserNotMatchException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.UserNotMatchException;
    public static final String EXC_ERROR_DESCRIPTION = "You can't change this entity";
    public static final String ERROR_MESSAGE = "Entity: `%s` id:`%s` doesn't belong to user with id:`%s`";


    public UserNotMatchException(Class aClass, User user, Long entityId) {
        super(String.format(ERROR_MESSAGE, aClass.getSimpleName(), entityId, user.getId()), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, aClass);
    }
}
