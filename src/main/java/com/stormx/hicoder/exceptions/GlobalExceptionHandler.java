package com.stormx.hicoder.exceptions;


import com.stormx.hicoder.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AppException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerAppException(Exception e) {
        logger.error("AppException: " + e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), null);
    }

    @ExceptionHandler({ValidationException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        logger.error("Validation Error: " + ex.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), null);
    }

    @ExceptionHandler({RuntimeException.class})
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = exception.getLocalizedMessage();
        if (exception instanceof BadRequestException || exception instanceof BadCredentialsException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorMessage = "Email or password is incorrect";
        }
        return new ErrorResponse(httpStatus, errorMessage, null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnwantedException(Exception e) {
        logger.error("Unknown error: " + e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Have an unknown error", null);
    }
}

