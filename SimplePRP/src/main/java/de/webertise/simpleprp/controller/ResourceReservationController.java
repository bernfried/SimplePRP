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
import de.webertise.simpleprp.model.ResourceReservation;
import de.webertise.simpleprp.service.ResourceReservationService;

/**
 * Handles requests for resourceReservations.
 */
@Controller
@RequestMapping("/resourceReservations")
public class ResourceReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceReservationController.class);

    @Autowired
    private ResourceReservationService resourceReservationService;

    /**
     * Get a resourceReservation by ID
     * 
     * @param resourceReservationId
     *            Id of the resourceReservation
     * @return ResourceReservation
     */
    @RequestMapping(value = "/{resourceReservationId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<ResourceReservation> getResourceReservationById(@PathVariable Long resourceReservationId) {
        logger.info("ResourceReservationController - getResourceReservationById: resourceReservationId = '" + resourceReservationId + "'");

        // get the resourceReservation by id
        ResourceReservation resourceReservation = resourceReservationService.get(resourceReservationId);

        // return http status 404 (not found)
        if (resourceReservation == null) {
            return new ResponseEntity<ResourceReservation>(HttpStatus.NOT_FOUND);
        }

        // return the resourceReservation found
        return new ResponseEntity<ResourceReservation>(resourceReservation, HttpStatus.OK);
    }

    /**
     * Get all resourceReservations
     * 
     * @return List of ResourceReservations
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<ResourceReservation>> getResourceReservations() {
        logger.info("ResourceReservationController - getResourceReservations: reached");

        // get the resourceReservation by id
        List<ResourceReservation> resourceReservations = resourceReservationService.findAll();

        JaxbList<ResourceReservation> jaxbList = new JaxbList<ResourceReservation>(resourceReservations);

        // return resourceReservation object as json / xml
        return new ResponseEntity<JaxbList<ResourceReservation>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a resourceReservation by id
     * 
     * @param resourceReservationId
     *            Id of the resourceReservation to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{resourceReservationId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceReservation> deleteResourceReservationById(@PathVariable Long resourceReservationId) throws Exception {
        logger.info("ResourceReservationController - deleteResourceReservationById: resourceReservationId = '" + resourceReservationId + "'");

        // check if id exists
        if (resourceReservationId != null && !resourceReservationService.exists(resourceReservationId)) {
            throw new ObjectNotFoundException("ResourceReservation with id '" + resourceReservationId + "' not found.");
        }

        // get resourceReservation object
        ResourceReservation deletedResourceReservation = resourceReservationService.get(resourceReservationId);

        // delete resourceReservation by id
        resourceReservationService.remove(resourceReservationId);

        // check if delete was successful
        if (!resourceReservationService.exists(resourceReservationId)) {
            throw new ObjectDeletionFailedException("ResourceReservation with id '" + resourceReservationId + "' could not be deleted.", deletedResourceReservation);
        }

        return new ResponseEntity<ResourceReservation>(deletedResourceReservation, HttpStatus.OK);
    }

    /**
     * Create a new resourceReservation
     * 
     * @param resourceReservation
     *            New resourceReservation object
     * @param builder
     *            Uri builder
     * @return Newly created resourceReservation
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceReservation> createResourceReservation(@RequestBody ResourceReservation resourceReservation, UriComponentsBuilder builder) throws Exception {
        logger.info("ResourceReservationController - createResourceReservation: reached");

        // check if a resourceReservation with that name already exists
        ResourceReservation existsResourceReservation = resourceReservationService.getByName(resourceReservation.getName());
        if (existsResourceReservation != null) {
            throw new ObjectExistsAlreadyException("ResourceReservation with name '" + resourceReservation.getName() + "' exists already.", existsResourceReservation);
        }

        // create resourceReservation
        ResourceReservation newResourceReservation = resourceReservationService.save(resourceReservation);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/resourceReservations/{id}").buildAndExpand(newResourceReservation.getId()).toUri());

        // return new resourceReservation
        return new ResponseEntity<ResourceReservation>(newResourceReservation, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing resourceReservation.
     * 
     * @param resourceReservation
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated resourceReservation object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<ResourceReservation> updateResourceReservation(@RequestBody ResourceReservation resourceReservation, UriComponentsBuilder builder) throws Exception {
        logger.info("ResourceReservationController - updateResourceReservation: reached");

        // check if a resourceReservation with that name exists
        ResourceReservation existsResourceReservation = resourceReservationService.getByName(resourceReservation.getName());
        if (existsResourceReservation == null) {
            throw new ObjectNotFoundException("ResourceReservation with name '" + resourceReservation.getName() + "' not found.");
        }

        // Update all changeable resourceReservation properties
        existsResourceReservation.setName(resourceReservation.getName());
        existsResourceReservation.setChangedAt(resourceReservation.getChangedAt());
        existsResourceReservation.setChangedBy(resourceReservation.getChangedBy());

        ResourceReservation updatedResourceReservation = resourceReservationService.save(existsResourceReservation);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/resourceReservations/{id}").buildAndExpand(updatedResourceReservation.getId()).toUri());

        // return updated resourceReservation
        return new ResponseEntity<ResourceReservation>(updatedResourceReservation, headers, HttpStatus.OK);
    }

}
