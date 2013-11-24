package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_MODULE")
@XmlRootElement(name = "module")
public class Module extends AbstractEntityObject {

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @ManyToMany
    @JoinTable(name = "PRP_MODULE_RESOURCEROLE", joinColumns = { @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCEROLE_ID", referencedColumnName = "ID") })
    private Set<ResourceRole> resourceRoles;

    @ManyToMany
    @JoinTable(name = "PRP_MODULE_RESOURCERESERVATION", joinColumns = { @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCERESERVATION_ID", referencedColumnName = "ID") })
    private Set<ResourceReservation> resourceReservations;

    @ManyToMany
    @JoinTable(name = "PRP_USER_MODULE", joinColumns = { @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
    private Set<User> users;

    @ManyToOne
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
     * @return the resourceRoles
     */
    @XmlElement(name = "resourceroles")
    public Set<ResourceRole> getResourceRoles() {
        return this.resourceRoles;
    }

    /**
     * @param resourceRoles
     *            the resourceRoles to set
     */
    public void setResourceRoles(Set<ResourceRole> resourceRoles) {
        this.resourceRoles = resourceRoles;
    }

    /**
     * @return the resourceReservations
     */
    @XmlElement(name = "resourcereservations")
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
     * @return the users
     */
    @XmlElement(name = "users")
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
     * @return the project
     */
    @XmlElement(name = "project")
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
        return "Client [name=" + name + ", toString()=" + super.toString() + "]";
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
        Module other = (Module) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
