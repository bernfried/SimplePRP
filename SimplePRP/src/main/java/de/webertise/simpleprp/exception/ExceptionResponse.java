package de.webertise.simpleprp.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exception")
public class ExceptionResponse {

    // *******************************************************
    // * Constants
    // *******************************************************

    public final static int EXCEPTION_CODE_OBJECT_NOT_FOUND = -100;
    public static final int EXCEPTION_CODE_OBJECT_EXISTS_ALREADY = -110;
    public static final int EXCEPTION_CODE_OBJECT_ACCESS_FORBIDDEN = -120;
    public static final int EXCEPTION_CODE_OBJECT_DELETION_FAILED = -130;

    public static final int EXCEPTION_CODE_METHOD_ARGUMENT_NOT_VALID = -200;
    public static final int EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_SUPPORTED = -210;
    public static final int EXCEPTION_CODE_HTTP_REQUEST_METHOD_NOT_SUPPORTED = -220;
    public static final int EXCEPTION_CODE_HTTP_MEDIA_TYPE_NOT_ACCEPTABLE = -230;

    public static final int EXCEPTION_CODE_AUTHENTICATION_REQUIRED = -300;

    // *******************************************************
    // * Properties
    // *******************************************************

    private int code;
    private String name;
    private String description;
    private Object object;

    // *******************************************************
    // * Constructors
    // *******************************************************

    public ExceptionResponse() {
    }

    public ExceptionResponse(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public ExceptionResponse(int code, String name, String description, Object object) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.object = object;
    }

    // *******************************************************
    // * Getter & Setter
    // *******************************************************

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public Object getObject() {
        return object;
    }

    @XmlElement
    public void setObject(Object object) {
        this.object = object;
    }

    // *******************************************************
    // * General Methods
    // *******************************************************

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.code;
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExceptionResponse other = (ExceptionResponse) obj;
        if (this.code != other.code)
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        } else if (!this.description.equals(other.description))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ExceptionResponse [code=" + this.code + ", name=" + this.name + ", description=" + this.description + "]";
    }

}
