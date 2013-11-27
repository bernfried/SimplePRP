package de.webertise.simpleprp.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_RESOURCERESERVATION")
@XmlRootElement(name = "resourcereservation")
public class ResourceReservation extends AbstractEntityObject {

    // *******************************************************
    // * Constants
    // *******************************************************
    public final static int STATUS_REQUESTED = 1;
    public final static int STATUS_CONFIRMED = 2;

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "STATUS")
    private int status;

    @Basic(optional = false)
    @Column(name = "DAY")
    private Date day;

    // yyyyMM
    @Basic(optional = false)
    @Column(name = "CALYEARWEEK")
    private String calYearWeek;

    // 0 - 100%
    @Basic(optional = false)
    @Column(name = "RESERVEDRATIO")
    private float reservedRatio;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCEROLE_ID")
    protected ResourceRole resourceRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODULE_ID", nullable = false)
    protected Module module;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public ResourceReservation() {
    }

    public ResourceReservation(String name) {
        this.name = name;
    }

    // *************************************************************************
    // * Getter & Setter
    // *************************************************************************

    /**
     * @return the name
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    @XmlElement
    public int getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the day
     */
    @XmlElement
    public Date getDay() {
        return this.day;
    }

    /**
     * @param day
     *            the day to set
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * @return the calYearWeek
     */
    @XmlElement
    public String getCalYearWeek() {
        return this.calYearWeek;
    }

    /**
     * @param calYearWeek
     *            the calYearWeek to set
     */
    public void setCalYearWeek(String calYearWeek) {
        this.calYearWeek = calYearWeek;
    }

    /**
     * @return the reservedRatio
     */
    @XmlElement
    public float getReservedRatio() {
        return this.reservedRatio;
    }

    /**
     * @param reservedRatio
     *            the reservedRatio to set
     */
    public void setReservedRatio(float reservedRatio) {
        this.reservedRatio = reservedRatio;
    }

    /**
     * @return the resourceRole
     */
    @XmlTransient
    @JsonIgnore
    public ResourceRole getResourceRole() {
        return this.resourceRole;
    }

    /**
     * @param resourceRole
     *            the resourceRole to set
     */
    public void setResourceRole(ResourceRole resourceRole) {
        this.resourceRole = resourceRole;
    }

    /**
     * @return the user
     */
    @XmlTransient
    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the module
     */
    @XmlTransient
    @JsonIgnore
    public Module getModule() {
        return this.module;
    }

    /**
     * @param module
     *            the module to set
     */
    public void setModule(Module module) {
        this.module = module;
    }

    // *************************************************************************
    // * General Methods
    // *************************************************************************

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ResourceReservation [name=" + this.name + ", status=" + this.status + ", day=" + this.day + ", calYearWeek=" + this.calYearWeek + ", reservedRatio=" + this.reservedRatio
                + ", toString()=" + super.toString() + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.calYearWeek == null) ? 0 : this.calYearWeek.hashCode());
        result = prime * result + ((this.day == null) ? 0 : this.day.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + Float.floatToIntBits(this.reservedRatio);
        result = prime * result + this.status;
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceReservation other = (ResourceReservation) obj;
        if (this.calYearWeek == null) {
            if (other.calYearWeek != null)
                return false;
        } else if (!this.calYearWeek.equals(other.calYearWeek))
            return false;
        if (this.day == null) {
            if (other.day != null)
                return false;
        } else if (!this.day.equals(other.day))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (Float.floatToIntBits(this.reservedRatio) != Float.floatToIntBits(other.reservedRatio))
            return false;
        if (this.status != other.status)
            return false;
        return true;
    }
}
