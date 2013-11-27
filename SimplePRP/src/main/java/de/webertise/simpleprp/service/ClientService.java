package de.webertise.simpleprp.service;

import de.webertise.simpleprp.model.Client;

public interface ClientService {

    /**
     * Get an Client by id.
     * 
     * @param id
     * @return Client
     */
    Client get(Long id);

    /**
     * Deletes a Client by id.
     * 
     * @param id
     *            ID of the Client
     */
    void remove(Long id);

    /**
     * Save a new Client or update an existing Client.
     * 
     * @param Client
     *            Client to change or create
     * @return New or updated Client
     */
    Client save(Client client);

    /**
     * Get an Client by name.
     * 
     * @param name
     *            Name of the Client
     * @return Client object
     */
    Client getByName(String name);

}
