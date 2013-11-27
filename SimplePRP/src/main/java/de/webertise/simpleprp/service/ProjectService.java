package de.webertise.simpleprp.service;

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

}
