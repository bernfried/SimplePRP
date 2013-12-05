package de.webertise.simpleprp.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    // ************************************************************
    // * Overridden Exceptions
    // ************************************************************

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("### RestResponseEntityExceptionHandler - handleMethodArgumentNotValid: reached.");

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<String>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_METHOD_ARGUMENT_NOT_VALID, "MethodArgumentNotValidException", ex.getMessage(), errors);
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("### RestResponseEntityExceptionHandler - handleHttpMediaTypeNotSupported: reached.");
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_SUPPORTED, "HttpMediaTypeNotSupportedException", ex.getMessage(), unsupported
                + " / " + supported);
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("### RestResponseEntityExceptionHandler - handleHttpMessageNotReadable: reached.");
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        ExceptionResponse exceptionResponse;
        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_SUPPORTED, exceptionName, message);
        } else {
            exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_SUPPORTED, "HttpMessageNotReadableException", ex.getMessage());
        }
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("### RestResponseEntityExceptionHandler - handleHttpRequestMethodNotSupported: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_HTTP_REQUEST_METHOD_NOT_SUPPORTED, "HttpRequestMethodNotSupportedException", ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("### RestResponseEntityExceptionHandler - handleHttpMediaTypeNotAcceptable: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, "HttpMediaTypeNotAcceptableException", ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // ************************************************************
    // * Custom Exceptions
    // ************************************************************

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
        logger.debug("### RestResponseEntityExceptionHandler - handleObjectNotFoundException: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_OBJECT_NOT_FOUND, "ObjectNotFoundException", ex.getMessage(), ex.getObject());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectExistsAlreadyException.class)
    protected ResponseEntity<ExceptionResponse> handleObjectExistsAlreadyException(ObjectExistsAlreadyException ex) {
        logger.debug("### RestResponseEntityExceptionHandler - handleObjectExistsAlreadyException: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_OBJECT_EXISTS_ALREADY, "ObjectExistsAlreadyException", ex.getMessage(), ex.getObject());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectAccessForbiddenException.class)
    protected ResponseEntity<ExceptionResponse> handleObjectAccessForbiddenException(ObjectAccessForbiddenException ex) {
        logger.debug("### RestResponseEntityExceptionHandler - handleObjectAccessForbiddenException: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_OBJECT_ACCESS_FORBIDDEN, "ObjectAccessForbiddenException", ex.getMessage(), ex.getObject());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectDeletionFailedException.class)
    protected ResponseEntity<ExceptionResponse> handleObjectDeletionFailedException(ObjectDeletionFailedException ex) {
        logger.debug("### RestResponseEntityExceptionHandler - handleObjectDeletionFailedException: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_OBJECT_DELETION_FAILED, "ObjectDeletionFailedException", ex.getMessage(), ex.getObject());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationRequiredException.class)
    protected ResponseEntity<ExceptionResponse> handleAuthenticationRequiredException(AuthenticationRequiredException ex) {
        logger.debug("### RestResponseEntityExceptionHandler - handleAuthenticationRequiredException: reached.");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ExceptionResponse.EXCEPTION_CODE_AUTHENTICATION_REQUIRED, "AuthenticationRequiredException", ex.getMessage(), ex.getObject());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}