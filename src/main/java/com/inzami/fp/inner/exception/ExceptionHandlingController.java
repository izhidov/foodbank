package com.inzami.fp.inner.exception;

import com.inzami.fp.domain.User;
import com.inzami.fp.exception.ApplicationException;
import com.inzami.fp.exception.BadRequestException;
import com.inzami.fp.exception.UserNotMatchException;
import com.inzami.fp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
@Controller
@Slf4j
public class ExceptionHandlingController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomErrorAttributes errorAttributes;

    @ExceptionHandler({LockedException.class})
    public void handleLockedException(HttpServletResponse response, LockedException e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler({ApplicationException.class})
    public void handleAppException1(HttpServletResponse response, ApplicationException e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getExcErrorDescription());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, BadRequestException.class})
    public void handleAppException2(HttpServletResponse response, Exception e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.PRECONDITION_FAILED.value());
    }

    @ExceptionHandler({DataIntegrityViolationException.class, JpaSystemException.class, TransactionSystemException.class})
    public void handleAppException3(HttpServletResponse response, Exception e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAppException4(HttpServletResponse response, Exception e) throws IOException {
        log.error("catch: ", e);
        User currentUser = userService.getCurrentUser();
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "Wrong user role for this rest resource: " + currentUser.getRole());
    }

    @ExceptionHandler(UserNotMatchException.class)
    public void handleAppException5(HttpServletResponse response, Exception e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(Model model, HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        String viewName = resolveViewName(request);
        if(StringUtils.isNotBlank(viewName)){
            ModelAndView errorModel = getErrorModel(request, viewName);
            return errorModel;
        }
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return null;
    }

    @ExceptionHandler(Throwable.class)
    public void handleAppException999(HttpServletResponse response, Exception e) throws IOException {
        log.error("catch: ", e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private ModelAndView getErrorModel(HttpServletRequest request, String viewName){
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> model = this.errorAttributes.getErrorAttributes(requestAttributes, false);

        return new ModelAndView(viewName, model);
    }

    private String resolveViewName(HttpServletRequest request){
        switch (request.getServletPath()){
            case "/api/client/search":
                return "client";
            default: return null;
        }
    }
}
