package de.webertise.simpleprp.exception;

import de.webertise.simpleprp.exception.general.AbstractException;

public class ObjectDeletionFailedException extends AbstractException {

    private static final long serialVersionUID = -3257536231500990208L;

    public ObjectDeletionFailedException() {
        super();
    }

    public ObjectDeletionFailedException(String message) {
        super(message);
    }

    public ObjectDeletionFailedException(String message, Object object) {
        super(message, object);
    }

}
