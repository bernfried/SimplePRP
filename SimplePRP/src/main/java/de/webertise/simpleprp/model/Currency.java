package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_CURRENCY")
@XmlRootElement(name = "currency")
public class Currency extends AbstractEntityObject {

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "TOEURORATE", nullable = false)
    private float toEuroRate;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private Set<Project> projects;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public Currency() {
    }

    public Currency(String name) {
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
     * @return the toEuroRate
     */
    @XmlElement
    public float getToEuroRate() {
        return this.toEuroRate;
    }

    /**
     * @param toEuroRate
     *            the toEuroRate to set
     */
    public void setToEuroRate(float toEuroRate) {
        this.toEuroRate = toEuroRate;
    }

    /**
     * @return the projects
     */
    @XmlTransient
    @JsonIgnore
    public Set<Project> getProjects() {
        return this.projects;
    }

    /**
     * @param projects
     *            the projects to set
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
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
        return "Currency [name=" + this.name + ", toEuroRate=" + this.toEuroRate + ", toString()=" + super.toString() + "]";
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
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + Float.floatToIntBits(this.toEuroRate);
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
        Currency other = (Currency) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (Float.floatToIntBits(this.toEuroRate) != Float.floatToIntBits(other.toEuroRate))
            return false;
        return true;
    }
}
