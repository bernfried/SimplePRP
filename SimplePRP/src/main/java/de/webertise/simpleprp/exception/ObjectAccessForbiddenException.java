package de.webertise.simpleprp.exception;

import de.webertise.simpleprp.exception.general.AbstractException;

public class ObjectAccessForbiddenException extends AbstractException {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectAccessForbiddenException() {
        super();
    }

    public ObjectAccessForbiddenException(String message) {
        super(message);
    }

    public ObjectAccessForbiddenException(String message, Object object) {
        super(message, object);
    }
}
