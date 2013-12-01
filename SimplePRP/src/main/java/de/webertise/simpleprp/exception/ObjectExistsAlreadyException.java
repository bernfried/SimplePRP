package de.webertise.simpleprp.exception;

public class ObjectExistsAlreadyException extends Exception {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectExistsAlreadyException() {
        super();
    }

    public ObjectExistsAlreadyException(String message) {
        super(message);
    }

}
