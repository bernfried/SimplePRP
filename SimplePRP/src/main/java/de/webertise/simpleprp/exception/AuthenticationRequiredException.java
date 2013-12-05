package de.webertise.simpleprp.exception;

import de.webertise.simpleprp.exception.general.AbstractException;

public class AuthenticationRequiredException extends AbstractException {

    private static final long serialVersionUID = -3257536231500990208L;

    public AuthenticationRequiredException() {
        super();
    }

    public AuthenticationRequiredException(String message) {
        super(message);
    }

    public AuthenticationRequiredException(String message, Object object) {
        super(message, object);
    }

}
