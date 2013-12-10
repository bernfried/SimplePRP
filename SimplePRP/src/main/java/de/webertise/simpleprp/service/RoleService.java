package de.webertise.simpleprp.service;

import java.util.List;

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
     * @param id
     *            ID of the Role
     */
    void remove(Long id);

    /**
     * Save a new Role or update an existing Role.
     * 
     * @param user
     *            Role to change or create
     * @return New or updated Role
     */
    Role save(Role role);

    /**
     * Get an Role by name.
     * 
     * @param name
     *            Name of the Role
     * @return Role object
     */
    Role getByName(String name);

    /**
     * Checks the existence of a role by id
     * 
     * @param id
     *            Role ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Finds all Roles
     * 
     * @return List of Role objects
     */
    List<Role> findAll();

}
