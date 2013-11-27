package de.webertise.simpleprp.service;

import de.webertise.simpleprp.model.ResourceReservation;

public interface ResourceReservationService {

    /**
     * Get an ResourceReservation by id.
     * 
     * @param id
     * @return ResourceReservation
     */
    ResourceReservation get(Long id);

    /**
     * Deletes a ResourceReservation by id.
     * 
     * @param id
     *            ID of the ResourceReservation
     */
    void remove(Long id);

    /**
     * Save a new ResourceReservation or update an existing ResourceReservation.
     * 
     * @param resourceReservation
     *            ResourceReservation to change or create
     * @return New or updated ResourceReservation
     */
    ResourceReservation save(ResourceReservation resourceReservation);

    /**
     * Get an ResourceReservation by name.
     * 
     * @param name
     *            Name of the ResourceReservation
     * @return ResourceReservation object
     */
    ResourceReservation getByName(String name);

}
