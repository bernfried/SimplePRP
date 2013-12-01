package de.webertise.simpleprp.exception;

public class ObjectAccessForbiddenException extends Exception {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectAccessForbiddenException() {
        super();
    }

    public ObjectAccessForbiddenException(String message) {
        super(message);
    }

}
