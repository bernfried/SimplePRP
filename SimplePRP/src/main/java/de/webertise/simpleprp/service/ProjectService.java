package de.webertise.simpleprp.service;

import java.util.List;

import de.webertise.simpleprp.model.Project;

public interface ProjectService {

    /**
     * Get an Project by id.
     * 
     * @param id
     * @return Project
     */
    Project get(Long id);

    /**
     * Deletes a Project by id.
     * 
     * @param id
     *            ID of the Project
     */
    void remove(Long id);

    /**
     * Save a new Project or update an existing Project.
     * 
     * @param project
     *            Project to change or create
     * @return New or updated Project
     */
    Project save(Project project);

    /**
     * Get an Project by name.
     * 
     * @param name
     *            Name of the Project
     * @return Project object
     */
    Project getByName(String name);

    /**
     * Checks the existence of a Project by id
     * 
     * @param id
     *            Project ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Finds all Projects
     * 
     * @return List of Project objects
     */
    List<Project> findAll();
}
