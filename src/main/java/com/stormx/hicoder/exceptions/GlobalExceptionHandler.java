package com.stormx.hicoder.exceptions;


import com.stormx.hicoder.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        log.error("Access Denied: {}", exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN, "Access denied, you must have role Admin", request.getRequestURI());
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request) {
        log.error("File too large: {}", exc.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorResponse(HttpStatus.EXPECTATION_FAILED, "File too large!", request.getRequestURI()));
    }
    @ExceptionHandler({AppException.class})
    public ResponseEntity<?> handlerAppException(AppException e, HttpServletRequest request) {
        log.error("AppException: {}", e.getLocalizedMessage());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode(), e.getLocalizedMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        log.error("Validation Error: {}", e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), ""));
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialException(BadCredentialsException exception, HttpServletRequest request) {
        log.error("Bad Credential: {}", exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Email or password is incorrect", request.getRequestURI());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        log.error("Bad Request: {}", exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), request.getRequestURI());
    }

    @MessageExceptionHandler()
    public ErrorResponse handleException(Exception e) {
        e.printStackTrace();
        log.error("Message Error: {}", e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), "/api/v1/chat/send");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmptyRequestEx(Exception e, HttpServletRequest request) {
        log.error("Empty Request: {}", e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage().split(":")[0], request.getRequestURI());
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoResourceFoundException(Exception exception, HttpServletRequest request) {
        log.error("Resource Not Found: {}", exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), request.getRequestURI());
    }


    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        exception.printStackTrace();
        log.error("Runtime Error: {}", exception.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", request.getRequestURI());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnwantedException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        log.error("Unknown error: {}", e.getLocalizedMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Have an unknown error", request.getRequestURI());
    }
}

