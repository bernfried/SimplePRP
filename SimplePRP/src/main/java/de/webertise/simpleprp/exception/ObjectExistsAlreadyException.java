package de.webertise.simpleprp.exception;

import de.webertise.simpleprp.exception.general.AbstractException;

public class ObjectExistsAlreadyException extends AbstractException {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectExistsAlreadyException() {
        super();
    }

    public ObjectExistsAlreadyException(String message) {
        super(message);
    }

    public ObjectExistsAlreadyException(String message, Object object) {
        super(message, object);
    }

}
