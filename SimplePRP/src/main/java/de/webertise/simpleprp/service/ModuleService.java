package de.webertise.simpleprp.service;

import de.webertise.simpleprp.model.Module;

public interface ModuleService {

    /**
     * Get an Module by id.
     * 
     * @param id
     * @return Module
     */
    Module get(Long id);

    /**
     * Deletes a Module by id.
     * 
     * @param id
     *            ID of the Module
     */
    void remove(Long id);

    /**
     * Save a new Module or update an existing Module.
     * 
     * @param Module
     *            Module to change or create
     * @return New or updated Module
     */
    Module save(Module module);

    /**
     * Get an Module by name.
     * 
     * @param name
     *            Name of the Module
     * @return Module object
     */
    Module getByName(String name);

}
