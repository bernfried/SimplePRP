package de.webertise.simpleprp.service;

import java.util.List;

import de.webertise.simpleprp.model.ResourceRole;

public interface ResourceRoleService {

    /**
     * Get an ResourceRole by id.
     * 
     * @param id
     * @return ResourceRole
     */
    ResourceRole get(Long id);

    /**
     * Deletes a ResourceRole by id.
     * 
     * @param id
     *            ID of the ResourceRole
     */
    void remove(Long id);

    /**
     * Save a new ResourceRole or update an existing ResourceRole.
     * 
     * @param resourceRole
     *            ResourceRole to change or create
     * @return New or updated ResourceRole
     */
    ResourceRole save(ResourceRole resourceRole);

    /**
     * Get an ResourceRole by name.
     * 
     * @param name
     *            Name of the ResourceRole
     * @return ResourceRole object
     */
    ResourceRole getByName(String name);

    /**
     * Checks the existence of a ResourceRole by id
     * 
     * @param id
     *            ResourceRole ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Finds all ResourceRoles
     * 
     * @return List of ResourceRole objects
     */
    List<ResourceRole> findAll();
}
