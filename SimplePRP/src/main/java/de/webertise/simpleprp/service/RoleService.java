package de.webertise.simpleprp.service;

import de.webertise.simpleprp.model.Role;

public interface RoleService {
	
	/**
	 * Get an Role by id.
	 * 
	 * @param id
	 * @return Role
	 */
	Role get(Long id);
	
	/**
	 * Deletes a Role by id.
	 * 
	 * @param id ID of the Role
	 */
	void remove(Long id);
	
	/** 
	 * Save a new Role or update an existing Role.
	 * 
	 * @param user Role to change or create
	 * @return New or updated Role
	 */
	Role save(Role role);
		
	/**
	 * Get an Role by login.
	 * 
	 * @param login Login of the Role
	 * @return Role object
	 */
	Role getByName(String name);


}
