package com.stormx.hicoder.exceptions;


import com.stormx.hicoder.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AppException.class})
    public ResponseEntity<?> handlerAppException(AppException e, HttpServletRequest request) {
        logger.error("AppException: " + e.getLocalizedMessage());
//        return new ErrorResponse(e.getStatusCode(), e.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getLocalizedMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex, HttpServletRequest request) {
        logger.error("Validation Error: " + ex.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), request.getRequestURI());
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialException(BadCredentialsException exception, HttpServletRequest request) {
        logger.error("Bad Credential: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Email or password is incorrect", request.getRequestURI());
    }
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        logger.error("Bad Request: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), request.getRequestURI());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        logger.error("Runtime Error: " + exception.getLocalizedMessage() );
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnwantedException(Exception e, HttpServletRequest request) {
        logger.error("Unknown error: "  +  e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Have an unknown error", request.getRequestURI());
    }
}

