package com.stormx.hicoder.exceptions;


import com.stormx.hicoder.common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(BadRequestException ex) {
        logger.error("User not found: " + ex.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), null);
    }
    @ExceptionHandler({AppException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerAppException(Exception e) {
        logger.error("AppException: " + e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), null);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        logger.error("Validation Error: " + ex.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), null);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        logger.error("Runtime Error: " + exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnwantedException(Exception e) {
        logger.error("Unknown error: " + e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Have an unknown error", null);
    }
}

