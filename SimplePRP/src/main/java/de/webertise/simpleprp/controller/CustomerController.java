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
import de.webertise.simpleprp.model.Customer;
import de.webertise.simpleprp.service.CustomerService;

/**
 * Handles requests for customers.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * Get a customer by ID
     * 
     * @param customerId
     *            Id of the customer
     * @return Customer
     */
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        logger.info("CustomerController - getCustomerById: customerId = '" + customerId + "'");

        // get the customer by id
        Customer customer = customerService.get(customerId);

        // return http status 404 (not found)
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        // return the customer found
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    /**
     * Get all customers
     * 
     * @return List of Customers
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<Customer>> getCustomers() {
        logger.info("CustomerController - getCustomers: reached");

        // get the customer by id
        List<Customer> customers = customerService.findAll();

        JaxbList<Customer> jaxbList = new JaxbList<Customer>(customers);

        // return customer object as json / xml
        return new ResponseEntity<JaxbList<Customer>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a customer by id
     * 
     * @param customerId
     *            Id of the customer to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long customerId) throws Exception {
        logger.info("CustomerController - deleteCustomerById: customerId = '" + customerId + "'");

        // check if id exists
        if (customerId != null && !customerService.exists(customerId)) {
            throw new ObjectNotFoundException("Customer with id '" + customerId + "' not found.");
        }

        // get customer object
        Customer deletedCustomer = customerService.get(customerId);

        // delete customer by id
        customerService.remove(customerId);

        // check if delete was successful
        if (!customerService.exists(customerId)) {
            throw new ObjectDeletionFailedException("Customer with id '" + customerId + "' could not be deleted.", deletedCustomer);
        }

        return new ResponseEntity<Customer>(deletedCustomer, HttpStatus.OK);
    }

    /**
     * Create a new customer
     * 
     * @param customer
     *            New customer object
     * @param builder
     *            Uri builder
     * @return Newly created customer
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) throws Exception {
        logger.info("CustomerController - createCustomer: reached");

        // check if a customer with that name already exists
        Customer existsCustomer = customerService.getByName(customer.getName());
        if (existsCustomer != null) {
            throw new ObjectExistsAlreadyException("Customer with name '" + customer.getName() + "' exists already.", existsCustomer);
        }

        // create customer
        Customer newCustomer = customerService.save(customer);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/customers/{id}").buildAndExpand(newCustomer.getId()).toUri());

        // return new customer
        return new ResponseEntity<Customer>(newCustomer, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer.
     * 
     * @param customer
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated customer object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) throws Exception {
        logger.info("CustomerController - updateCustomer: reached");

        // check if a customer with that name exists
        Customer existsCustomer = customerService.getByName(customer.getName());
        if (existsCustomer == null) {
            throw new ObjectNotFoundException("Customer with name '" + customer.getName() + "' not found.");
        }

        // Update all changeable customer properties
        existsCustomer.setName(customer.getName());
        existsCustomer.setChangedAt(customer.getChangedAt());
        existsCustomer.setChangedBy(customer.getChangedBy());

        Customer updatedCustomer = customerService.save(existsCustomer);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/customers/{id}").buildAndExpand(updatedCustomer.getId()).toUri());

        // return updated customer
        return new ResponseEntity<Customer>(updatedCustomer, headers, HttpStatus.OK);
    }

}
