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
import de.webertise.simpleprp.model.ResourceRole;
import de.webertise.simpleprp.service.ResourceRoleService;

/**
 * Handles requests for resourceRoles.
 */
@Controller
@RequestMapping("/resourceRoles")
public class ResourceRoleController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRoleController.class);

    @Autowired
    private ResourceRoleService resourceRoleService;

    /**
     * Get a resourceRole by ID
     * 
     * @param resourceRoleId
     *            Id of the resourceRole
     * @return ResourceRole
     */
    @RequestMapping(value = "/{resourceRoleId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<ResourceRole> getResourceRoleById(@PathVariable Long resourceRoleId) {
        logger.info("ResourceRoleController - getResourceRoleById: resourceRoleId = '" + resourceRoleId + "'");

        // get the resourceRole by id
        ResourceRole resourceRole = resourceRoleService.get(resourceRoleId);

        // return http status 404 (not found)
        if (resourceRole == null) {
            return new ResponseEntity<ResourceRole>(HttpStatus.NOT_FOUND);
        }

        // return the resourceRole found
        return new ResponseEntity<ResourceRole>(resourceRole, HttpStatus.OK);
    }

    /**
     * Get all resourceRoles
     * 
     * @return List of ResourceRoles
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<ResourceRole>> getResourceRoles() {
        logger.info("ResourceRoleController - getResourceRoles: reached");

        // get the resourceRole by id
        List<ResourceRole> resourceRoles = resourceRoleService.findAll();

        JaxbList<ResourceRole> jaxbList = new JaxbList<ResourceRole>(resourceRoles);

        // return resourceRole object as json / xml
        return new ResponseEntity<JaxbList<ResourceRole>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a resourceRole by id
     * 
     * @param resourceRoleId
     *            Id of the resourceRole to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{resourceRoleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceRole> deleteResourceRoleById(@PathVariable Long resourceRoleId) throws Exception {
        logger.info("ResourceRoleController - deleteResourceRoleById: resourceRoleId = '" + resourceRoleId + "'");

        // check if id exists
        if (resourceRoleId != null && !resourceRoleService.exists(resourceRoleId)) {
            throw new ObjectNotFoundException("ResourceRole with id '" + resourceRoleId + "' not found.");
        }

        // get resourceRole object
        ResourceRole deletedResourceRole = resourceRoleService.get(resourceRoleId);

        // delete resourceRole by id
        resourceRoleService.remove(resourceRoleId);

        // check if delete was successful
        if (!resourceRoleService.exists(resourceRoleId)) {
            throw new ObjectDeletionFailedException("ResourceRole with id '" + resourceRoleId + "' could not be deleted.", deletedResourceRole);
        }

        return new ResponseEntity<ResourceRole>(deletedResourceRole, HttpStatus.OK);
    }

    /**
     * Create a new resourceRole
     * 
     * @param resourceRole
     *            New resourceRole object
     * @param builder
     *            Uri builder
     * @return Newly created resourceRole
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceRole> createResourceRole(@RequestBody ResourceRole resourceRole, UriComponentsBuilder builder) throws Exception {
        logger.info("ResourceRoleController - createResourceRole: reached");

        // check if a resourceRole with that name already exists
        ResourceRole existsResourceRole = resourceRoleService.getByName(resourceRole.getName());
        if (existsResourceRole != null) {
            throw new ObjectExistsAlreadyException("ResourceRole with name '" + resourceRole.getName() + "' exists already.", existsResourceRole);
        }

        // create resourceRole
        ResourceRole newResourceRole = resourceRoleService.save(resourceRole);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/resourceRoles/{id}").buildAndExpand(newResourceRole.getId()).toUri());

        // return new resourceRole
        return new ResponseEntity<ResourceRole>(newResourceRole, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing resourceRole.
     * 
     * @param resourceRole
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated resourceRole object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceRole> updateResourceRole(@RequestBody ResourceRole resourceRole, UriComponentsBuilder builder) throws Exception {
        logger.info("ResourceRoleController - updateResourceRole: reached");

        // check if a resourceRole with that name exists
        ResourceRole existsResourceRole = resourceRoleService.getByName(resourceRole.getName());
        if (existsResourceRole == null) {
            throw new ObjectNotFoundException("ResourceRole with name '" + resourceRole.getName() + "' not found.");
        }

        // Update all changeable resourceRole properties
        existsResourceRole.setName(resourceRole.getName());
        existsResourceRole.setChangedAt(resourceRole.getChangedAt());
        existsResourceRole.setChangedBy(resourceRole.getChangedBy());

        ResourceRole updatedResourceRole = resourceRoleService.save(existsResourceRole);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/resourceRoles/{id}").buildAndExpand(updatedResourceRole.getId()).toUri());

        // return updated resourceRole
        return new ResponseEntity<ResourceRole>(updatedResourceRole, headers, HttpStatus.OK);
    }

}
