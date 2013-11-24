package de.webertise.simpleprp.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_CLIENT")
public class Client extends AbstractEntityObject {

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
	@JoinTable(name = "PRP_USER_CLIENT", joinColumns = { @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private Set<User> users;

	// *************************************************************************
	// * Constructors
	// *************************************************************************

	public Client() {
	}

	public Client(String name) {
		this.name = name;
	}

	// *************************************************************************
	// * Getter & Setter
	// *************************************************************************

	/**
	 * @return the name
	 */
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
		Client other = (Client) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
