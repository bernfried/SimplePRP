package de.webertise.simpleprp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_RESOURCERESERVATION")
public class ResourceReservation extends AbstractEntityObject {

	// *******************************************************
	// * Specific Entity Properties
	// *******************************************************

	@Basic(optional = false)
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	// *******************************************************
	// * Relationships
	// *******************************************************

	@ManyToOne
	@JoinColumn(name = "RESOURCEROLE_ID", nullable = false)
	protected ResourceRole resourceRole;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	protected User user;

	@ManyToOne
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
		ResourceReservation other = (ResourceReservation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}