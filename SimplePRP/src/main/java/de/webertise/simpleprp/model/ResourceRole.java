package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_RESOURCEROLE")
@XmlRootElement(name = "resourcerole")
public class ResourceRole extends AbstractEntityObject {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRP_MODULE_RESOURCEROLE", joinColumns = { @JoinColumn(name = "RESOURCEROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID") })
    private Set<Module> modules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRP_USER_RESOURCEROLE", joinColumns = { @JoinColumn(name = "RESOURCEROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
    private Set<User> users;

    @OneToMany(mappedBy = "resourceRole", fetch = FetchType.LAZY)
    private Set<ResourceReservation> resourceReservation;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public ResourceRole() {
    }

    public ResourceRole(String name) {
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
     * @return the modules
     */
    @XmlTransient
    @JsonIgnore
    public Set<Module> getModules() {
        return this.modules;
    }

    /**
     * @param modules
     *            the modules to set
     */
    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the users
     */
    @XmlTransient
    @JsonIgnore
    public Set<User> getUsers() {
        return this.users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * @return the resourceReservation
     */
    @XmlTransient
    @JsonIgnore
    public Set<ResourceReservation> getResourceReservation() {
        return this.resourceReservation;
    }

    /**
     * @param resourceReservation
     *            the resourceReservation to set
     */
    public void setResourceReservation(Set<ResourceReservation> resourceReservation) {
        this.resourceReservation = resourceReservation;
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
        return "Role [name=" + name + ", toString()=" + super.toString() + "]";
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ResourceRole other = (ResourceRole) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
