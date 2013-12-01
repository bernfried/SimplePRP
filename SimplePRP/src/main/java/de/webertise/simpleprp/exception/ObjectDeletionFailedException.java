package de.webertise.simpleprp.exception;

public class ObjectDeletionFailedException extends Exception {

    private static final long serialVersionUID = -3257536231500990208L;

    private Object object;

    public ObjectDeletionFailedException() {
        super();
    }

    public ObjectDeletionFailedException(String message) {
        super(message);
    }

    public ObjectDeletionFailedException(String message, Object object) {
        super(message);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
