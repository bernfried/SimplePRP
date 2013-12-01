package de.webertise.simpleprp.exception.general;

public abstract class AbstractException extends Exception {

    private static final long serialVersionUID = 4476299259235904363L;

    private Object object;

    public AbstractException() {
    }

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
