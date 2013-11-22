package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Role;

public interface RoleDao extends CrudRepository<Role, Long> {
	
	/**
	 * Get a Role by name
	 * 
	 * @param name Name of the Role
	 * @return Role
	 */
	Role getByName(String name);

}
