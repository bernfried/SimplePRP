package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Client;

public interface ClientDao extends CrudRepository<Client, Long> {

    /**
     * Get a Client by name
     * 
     * @param name
     *            Name of the Client
     * @return Client
     */
    Client getByName(String name);

}
