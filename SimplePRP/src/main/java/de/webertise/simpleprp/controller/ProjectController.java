package de.webertise.simpleprp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import de.webertise.simpleprp.exception.ObjectDeletionFailedException;
import de.webertise.simpleprp.exception.ObjectExistsAlreadyException;
import de.webertise.simpleprp.exception.ObjectNotFoundException;
import de.webertise.simpleprp.helper.xml.JaxbList;
import de.webertise.simpleprp.model.Project;
import de.webertise.simpleprp.service.ProjectService;

/**
 * Handles requests for projects.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /**
     * Get a project by ID
     * 
     * @param projectId
     *            Id of the project
     * @return Project
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        logger.info("ProjectController - getProjectById: projectId = '" + projectId + "'");

        // get the project by id
        Project project = projectService.get(projectId);

        // return http status 404 (not found)
        if (project == null) {
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }

        // return the project found
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    /**
     * Get all projects
     * 
     * @return List of Projects
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<Project>> getProjects() {
        logger.info("ProjectController - getProjects: reached");

        // get the project by id
        List<Project> projects = projectService.findAll();

        JaxbList<Project> jaxbList = new JaxbList<Project>(projects);

        // return project object as json / xml
        return new ResponseEntity<JaxbList<Project>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a project by id
     * 
     * @param projectId
     *            Id of the project to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<Project> deleteProjectById(@PathVariable Long projectId) throws Exception {
        logger.info("ProjectController - deleteProjectById: projectId = '" + projectId + "'");

        // check if id exists
        if (projectId != null && !projectService.exists(projectId)) {
            throw new ObjectNotFoundException("Project with id '" + projectId + "' not found.");
        }

        // get project object
        Project deletedProject = projectService.get(projectId);

        // delete project by id
        projectService.remove(projectId);

        // check if delete was successful
        if (!projectService.exists(projectId)) {
            throw new ObjectDeletionFailedException("Project with id '" + projectId + "' could not be deleted.", deletedProject);
        }

        return new ResponseEntity<Project>(deletedProject, HttpStatus.OK);
    }

    /**
     * Create a new project
     * 
     * @param project
     *            New project object
     * @param builder
     *            Uri builder
     * @return Newly created project
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<Project> createProject(@RequestBody Project project, UriComponentsBuilder builder) throws Exception {
        logger.info("ProjectController - createProject: reached");

        // check if a project with that name already exists
        Project existsProject = projectService.getByName(project.getName());
        if (existsProject != null) {
            throw new ObjectExistsAlreadyException("Project with name '" + project.getName() + "' exists already.", existsProject);
        }

        // create project
        Project newProject = projectService.save(project);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/projects/{id}").buildAndExpand(newProject.getId()).toUri());

        // return new project
        return new ResponseEntity<Project>(newProject, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing project.
     * 
     * @param project
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated project object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<Project> updateProject(@RequestBody Project project, UriComponentsBuilder builder) throws Exception {
        logger.info("ProjectController - updateProject: reached");

        // check if a project with that name exists
        Project existsProject = projectService.getByName(project.getName());
        if (existsProject == null) {
            throw new ObjectNotFoundException("Project with name '" + project.getName() + "' not found.");
        }

        // Update all changeable project properties
        existsProject.setName(project.getName());
        existsProject.setChangedAt(project.getChangedAt());
        existsProject.setChangedBy(project.getChangedBy());

        Project updatedProject = projectService.save(existsProject);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/projects/{id}").buildAndExpand(updatedProject.getId()).toUri());

        // return updated project
        return new ResponseEntity<Project>(updatedProject, headers, HttpStatus.OK);
    }

}
