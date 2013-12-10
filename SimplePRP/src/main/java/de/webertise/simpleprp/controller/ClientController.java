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
import de.webertise.simpleprp.model.Client;
import de.webertise.simpleprp.service.ClientService;

/**
 * Handles requests for clients.
 */
@Controller
@RequestMapping("/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    /**
     * Get a client by ID
     * 
     * @param clientId
     *            Id of the client
     * @return Client
     */
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        logger.info("ClientController - getClientById: clientId = '" + clientId + "'");

        // get the client by id
        Client client = clientService.get(clientId);

        // return http status 404 (not found)
        if (client == null) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }

        // return the client found
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    /**
     * Get all clients
     * 
     * @return List of Clients
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<Client>> getClients() {
        logger.info("ClientController - getClients: reached");

        // get the client by id
        List<Client> clients = clientService.findAll();

        JaxbList<Client> jaxbList = new JaxbList<Client>(clients);

        // return client object as json / xml
        return new ResponseEntity<JaxbList<Client>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a client by id
     * 
     * @param clientId
     *            Id of the client to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{clientId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<Client> deleteClientById(@PathVariable Long clientId) throws Exception {
        logger.info("ClientController - deleteClientById: clientId = '" + clientId + "'");

        // check if id exists
        if (clientId != null && !clientService.exists(clientId)) {
            throw new ObjectNotFoundException("Client with id '" + clientId + "' not found.");
        }

        // get client object
        Client deletedClient = clientService.get(clientId);

        // delete client by id
        clientService.remove(clientId);

        // check if delete was successful
        if (!clientService.exists(clientId)) {
            throw new ObjectDeletionFailedException("Client with id '" + clientId + "' could not be deleted.", deletedClient);
        }

        return new ResponseEntity<Client>(deletedClient, HttpStatus.OK);
    }

    /**
     * Create a new client
     * 
     * @param client
     *            New client object
     * @param builder
     *            Uri builder
     * @return Newly created client
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<Client> createClient(@RequestBody Client client, UriComponentsBuilder builder) throws Exception {
        logger.info("ClientController - createClient: reached");

        // check if a client with that name already exists
        Client existsClient = clientService.getByName(client.getName());
        if (existsClient != null) {
            throw new ObjectExistsAlreadyException("Client with name '" + client.getName() + "' exists already.", existsClient);
        }

        // create client
        Client newClient = clientService.save(client);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/clients/{id}").buildAndExpand(newClient.getId()).toUri());

        // return new client
        return new ResponseEntity<Client>(newClient, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing client.
     * 
     * @param client
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated client object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<Client> updateClient(@RequestBody Client client, UriComponentsBuilder builder) throws Exception {
        logger.info("ClientController - updateClient: reached");

        // check if a client with that name exists
        Client existsClient = clientService.getByName(client.getName());
        if (existsClient == null) {
            throw new ObjectNotFoundException("Client with name '" + client.getName() + "' not found.");
        }

        // Update all changeable client properties
        existsClient.setName(client.getName());
        existsClient.setChangedAt(client.getChangedAt());
        existsClient.setChangedBy(client.getChangedBy());

        Client updatedClient = clientService.save(existsClient);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/clients/{id}").buildAndExpand(updatedClient.getId()).toUri());

        // return updated client
        return new ResponseEntity<Client>(updatedClient, headers, HttpStatus.OK);
    }

}
