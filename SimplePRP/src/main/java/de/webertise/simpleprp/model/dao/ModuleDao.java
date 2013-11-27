package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Module;

public interface ModuleDao extends CrudRepository<Module, Long> {

    /**
     * Get a Module by name
     * 
     * @param name
     *            Name of the Module
     * @return Module
     */
    Module getByName(String name);

}
