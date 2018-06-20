package com.inzami.fp.inner.exception;

import com.inzami.fp.domain.User;
import com.inzami.fp.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.*;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Value("${fp.debug}")
    private Boolean debug;
    @Value("#{systemProperties['server.error.path'] ?: '/error'}")
    private String serverErrorPath;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, debug);

        addCustomAttributes(errorAttributes);
        addServletRequestAttributes(errorAttributes);
        addExceptionAttributes(errorAttributes, requestAttributes);

        return errorAttributes;
    }

    private void addCustomAttributes(Map<String, Object> errorAttributes) {
        User authUser = (User) request.getAttribute("user");
        if (Objects.nonNull(authUser)) {
            errorAttributes.put("authUserId", authUser.getId());
            errorAttributes.put("authUserEmail", authUser.getEmail());
        }
    }


    private void addServletRequestAttributes(Map<String, Object> errorAttributes) {
        String url = request.getRequestURL().toString();
        errorAttributes.put("url", url.split(serverErrorPath)[0]);

        errorAttributes.put("method", request.getMethod());
        errorAttributes.put("params", request.getParameterMap());
        errorAttributes.put("post", request.getAttribute("post"));

        Map headers = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        errorAttributes.put("headers", headers);
    }

    private void addExceptionAttributes(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {

        List<CustomErrorDescription> customErrorDescriptions = new ArrayList<>();
        Throwable exception = super.getError(requestAttributes);

        while (exception != null) {
            if (exception instanceof MethodArgumentNotValidException) {
                List<ObjectError> dataBindingAndValidationErrors = (List) errorAttributes.get("errors");
                customErrorDescriptions = CustomErrorDescription.fromErrors(dataBindingAndValidationErrors);
                break;
            }
            if (exception instanceof MissingServletRequestParameterException) {
                customErrorDescriptions = CustomErrorDescription.fromException((MissingServletRequestParameterException) exception);
                break;
            }
            if (exception instanceof ConstraintViolationException) {
                customErrorDescriptions = CustomErrorDescription.fromException((ConstraintViolationException) exception);
                break;
            } else if (exception instanceof ApplicationException) {
                customErrorDescriptions = CustomErrorDescription.fromException((ApplicationException) exception);
                break;
            } else if (exception instanceof SQLException) {
                customErrorDescriptions = CustomErrorDescription.fromException((SQLException) exception);
                break;
            }
            exception = exception.getCause();
        }

        if (customErrorDescriptions.size() != 0) {
            errorAttributes.put("errors", customErrorDescriptions);
        }
    }

}
