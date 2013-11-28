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
        logger.debug("### RestResponseEntityExceptionHandler - handleIOException: reached.");

        ExceptionResponse exceptionResponse = new ExceptionResponse("ObjectNotFoundException", ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}