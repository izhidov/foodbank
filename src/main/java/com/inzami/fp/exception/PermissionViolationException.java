package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;
import com.inzami.fp.domain.User;
import com.inzami.fp.enums.PermissionType;

public class PermissionViolationException extends ApplicationException {

    public static final ExceptionCode EXC_ERROR_CODE = ExceptionCode.WrongUserRoleForActionException;
    public static final String EXC_ERROR_DESCRIPTION = "User doesn't have permission to perform action";
    public static final String ERROR_MESSAGE = "User:%s needs permission: %s";

    public PermissionViolationException(PermissionType permissionType, String email) {
        super(String.format(ERROR_MESSAGE, email, permissionType), EXC_ERROR_CODE, EXC_ERROR_DESCRIPTION, User.class, null);
    }
}
