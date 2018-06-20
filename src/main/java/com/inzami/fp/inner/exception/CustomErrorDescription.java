package com.inzami.fp.inner.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inzami.fp.exception.ApplicationException;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
public class CustomErrorDescription {

    private String objectName;
    private String field;
    private String code;
    private String message;

    public static List<CustomErrorDescription> fromErrors(List<ObjectError> objectErrors) {
        List<CustomErrorDescription> errors = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            CustomErrorDescription customErrorDescription;
            if (objectError instanceof FieldError) {
                customErrorDescription = fromFieldError((FieldError) objectError);
            } else {
                customErrorDescription = fromObjectError(objectError);
            }

            errors.add(customErrorDescription);
        }

        return errors;
    }

    private static CustomErrorDescription fromFieldError(FieldError fieldError) {
        CustomErrorDescription customErrorDescription = new CustomErrorDescription();
        customErrorDescription.objectName = fieldError.getObjectName();
        customErrorDescription.field = fieldError.getField();
        customErrorDescription.code = fieldError.getCode();
        customErrorDescription.message = fieldError.getDefaultMessage();

        return customErrorDescription;
    }

    private static CustomErrorDescription fromObjectError(ObjectError objectError) {
        CustomErrorDescription customErrorDescription = new CustomErrorDescription();
        customErrorDescription.objectName = objectError.getObjectName();
        customErrorDescription.code = objectError.getCode();
        customErrorDescription.message = objectError.getDefaultMessage();

        return customErrorDescription;
    }

    public static List<CustomErrorDescription> fromException(SQLException e) {
        List<CustomErrorDescription> errors = new ArrayList<>(1);
        CustomErrorDescription customErrorDescription = new CustomErrorDescription();

        customErrorDescription.objectName = "SQL CONSTRAINT";
        customErrorDescription.code = e.getSQLState();
        customErrorDescription.message = e.getMessage();

        errors.add(customErrorDescription);
        return errors;
    }

    public static List<CustomErrorDescription> fromException(ApplicationException e) {
        List<CustomErrorDescription> errors = new ArrayList<>(1);
        CustomErrorDescription customErrorDescription = new CustomErrorDescription();

        customErrorDescription.objectName = (!Objects.isNull(e.getExcClass())) ? e.getExcClass().getSimpleName() : null;
        customErrorDescription.field = e.getExcField();
        customErrorDescription.message = e.getMessage();

        errors.add(customErrorDescription);
        return errors;
    }

    public static List<CustomErrorDescription> fromException(ConstraintViolationException e) {
        List<CustomErrorDescription> errors = new ArrayList<>();

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            CustomErrorDescription customErrorDescription = new CustomErrorDescription();

            customErrorDescription.objectName = constraintViolation.getRootBean().getClass().getSimpleName();
            customErrorDescription.field = constraintViolation.getPropertyPath().toString();
            customErrorDescription.code = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            customErrorDescription.message = constraintViolation.getMessage();

            errors.add(customErrorDescription);
        }

        return errors;
    }

    public static List<CustomErrorDescription> fromException(MissingServletRequestParameterException e) {
        List<CustomErrorDescription> errors = new ArrayList<>();
        CustomErrorDescription customErrorDescription = new CustomErrorDescription();
        customErrorDescription.objectName = "Missing request parameter";
        customErrorDescription.field = e.getParameterName();
        customErrorDescription.message = e.getMessage();
        errors.add(customErrorDescription);
        return errors;
    }
}
