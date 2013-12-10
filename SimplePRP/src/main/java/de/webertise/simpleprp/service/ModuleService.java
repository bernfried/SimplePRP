package de.webertise.simpleprp.service;

import java.util.List;

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

    /**
     * Checks the existence of a module by id
     * 
     * @param id
     *            Module ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Finds all Modules
     * 
     * @return List of Module objects
     */
    List<Module> findAll();
}
