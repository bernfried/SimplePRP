package de.webertise.simpleprp.model;

import java.util.List;

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
@Table(name = "PRP_CUSTOMER")
@XmlRootElement(name = "customer")
public class Customer extends AbstractEntityObject {

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    // *******************************************************
    // * Relationships
    // *******************************************************

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Project> projects;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public Customer() {
    }

    public Customer(String name) {
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
     * @return the projects
     */
    @XmlTransient
    @JsonIgnore
    public List<Project> getProjects() {
        return this.projects;
    }

    /**
     * @param projects
     *            the projects to set
     */
    public void setProjects(List<Project> projects) {
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
        return "Customer [name=" + this.name + ", toString()=" + super.toString() + "]";
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
        Customer other = (Customer) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }
}
