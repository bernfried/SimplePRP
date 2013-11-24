package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_PROJECT")
@XmlRootElement(name = "project")
public class Project extends AbstractEntityObject {

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @OneToMany(mappedBy = "project")
    private Set<Module> modules;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    protected Customer customer;

    @ManyToMany
    @JoinTable(name = "PRP_PROJECT_USER", joinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
    private Set<User> users;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public Project() {
    }

    public Project(String name) {
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
    @XmlElement(name = "modules")
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
     * @return the customer
     */
    @XmlElement(name = "customer")
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Project other = (Project) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
