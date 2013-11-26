package de.webertise.simpleprp.model.general;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * @author BernfriedHowe
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement(name = "test")
public abstract class AbstractEntityObject {

    // *******************************************************
    // * General properties
    // *******************************************************
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CHANGEDAT")
    private Date changedAt;

    @Column(name = "CHANGEDBY")
    private String changedBy;

    @Version
    @Column(name = "VERSION", length = 255)
    private long version;

    /**
     * @return the id
     */
    @XmlElement
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the createdAt
     */
    @XmlElement
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the createdBy
     */
    @XmlElement
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the changedAt
     */
    @XmlElement
    public Date getChangedAt() {
        return changedAt;
    }

    /**
     * @param changedAt
     *            the changedAt to set
     */
    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    /**
     * @return the changedBy
     */
    @XmlElement
    public String getChangedBy() {
        return changedBy;
    }

    /**
     * @param changedBy
     *            the changedBy to set
     */
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    /**
     * @return the version
     */
    @XmlElement
    public long getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((changedAt == null) ? 0 : changedAt.hashCode());
        result = prime * result + ((changedBy == null) ? 0 : changedBy.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (version ^ (version >>> 32));
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
        AbstractEntityObject other = (AbstractEntityObject) obj;
        if (changedAt == null) {
            if (other.changedAt != null)
                return false;
        } else if (!changedAt.equals(other.changedAt))
            return false;
        if (changedBy == null) {
            if (other.changedBy != null)
                return false;
        } else if (!changedBy.equals(other.changedBy))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (id != other.id)
            return false;
        if (version != other.version)
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
        return "AbstractEntityObject [id=" + id + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", changedAt=" + changedAt + ", changedBy=" + changedBy + ", version=" + version + "]";
    }

}