package com.inzami.fp.exception;

import com.inzami.fp.enums.ExceptionCode;
import com.inzami.fp.util.AuthUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ApplicationException extends RuntimeException {

    private ExceptionCode excErrorCode = ExceptionCode.ApplicationException;
    private String excErrorDescription = "Application exception";
    private Class excClass;
    private String excField;
    private String userName;

    public ApplicationException(String message, ExceptionCode excErrorCode, String excErrorDescription, Class excClass, String excField) {
        super(message);
        this.excErrorCode = excErrorCode;
        this.excErrorDescription = excErrorDescription;
        this.excClass = excClass;
        this.excField = excField;
        this.userName = Objects.isNull(AuthUtils.getAuthUser()) ? null : AuthUtils.getAuthUser().getUsername();
    }

    public ApplicationException(String message, ExceptionCode excErrorCode, String excErrorDescription, Class excClass) {
        super(message);
        this.excErrorCode = excErrorCode;
        this.excErrorDescription = excErrorDescription;
        this.excClass = excClass;
        this.userName = Objects.isNull(AuthUtils.getAuthUser()) ? null : AuthUtils.getAuthUser().getUsername();
    }

    public ApplicationException(String message, ExceptionCode excErrorCode, String excErrorDescription, Class excClass, String excField, Throwable cause) {
        this(message, excErrorCode, excErrorDescription, excClass, excField);
        initCause(cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, ExceptionCode excErrorCode) {
        super(message);
        this.excErrorCode = excErrorCode;
    }
}
