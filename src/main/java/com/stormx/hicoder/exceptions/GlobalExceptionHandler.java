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

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        logger.error("Validation Error: " + ex.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), null);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ErrorResponse handleBadCredentialException(BadCredentialsException exception){
        logger.error("Bad Credential: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Email or password is incorrect", null);
    }
    @ExceptionHandler({BadRequestException.class})
    public ErrorResponse handleBadRequestException(BadRequestException exception){
        logger.error("Bad Request: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), null);
    }

    @ExceptionHandler({RuntimeException.class})
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        logger.error("Runtime Error: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnwantedException(Exception e) {
        logger.error("Unknown error: " + e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Have an unknown error", null);
    }
}

