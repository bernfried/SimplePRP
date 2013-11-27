package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.ResourceRole;

public interface ResourceRoleDao extends CrudRepository<ResourceRole, Long> {

    /**
     * Get a ResourceRole by name
     * 
     * @param name
     *            Name of the ResourceRole
     * @return ResourceRole
     */
    ResourceRole getByName(String name);

}
