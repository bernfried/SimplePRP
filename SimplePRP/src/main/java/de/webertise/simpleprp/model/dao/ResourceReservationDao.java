package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.ResourceReservation;

public interface ResourceReservationDao extends CrudRepository<ResourceReservation, Long> {

    /**
     * Get a ResourceReservation by name
     * 
     * @param name
     *            Name of the ResourceReservation
     * @return ResourceReservation
     */
    ResourceReservation getByName(String name);

}
