package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Project;

public interface ProjectDao extends CrudRepository<Project, Long> {

    /**
     * Get a Project by name
     * 
     * @param name
     *            Name of the Project
     * @return Project
     */
    Project getByName(String name);

}
