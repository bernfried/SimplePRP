package de.webertise.simpleprp.exception;

import de.webertise.simpleprp.exception.general.AbstractException;

public class ObjectNotFoundException extends AbstractException {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Object object) {
        super(message, object);
    }

}
