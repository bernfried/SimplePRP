package de.webertise.simpleprp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

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

}