package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_MODULE")
@XmlRootElement(name = "module")
public class Module extends AbstractEntityObject {

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Basic(optional = true)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private Set<ResourceReservation> resourceReservations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    protected Project project;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public Module() {
    }

    public Module(String name) {
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
     * @return the description
     */
    @XmlElement
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the resourceReservations
     */
    @XmlTransient
    @JsonIgnore
    public Set<ResourceReservation> getResourceReservations() {
        return this.resourceReservations;
    }

    /**
     * @param resourceReservations
     *            the resourceReservations to set
     */
    public void setResourceReservations(Set<ResourceReservation> resourceReservations) {
        this.resourceReservations = resourceReservations;
    }

    /**
     * @return the project
     */
    @XmlTransient
    @JsonIgnore
    public Project getProject() {
        return this.project;
    }

    /**
     * @param project
     *            the project to set
     */
    public void setProject(Project project) {
        this.project = project;
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
        return "Module [name=" + this.name + ", description=" + this.description + ", toString()=" + super.toString() + "]";
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Module other = (Module) obj;
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
}
