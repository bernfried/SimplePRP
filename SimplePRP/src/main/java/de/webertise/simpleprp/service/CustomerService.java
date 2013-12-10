package de.webertise.simpleprp.service;

import java.util.List;

import de.webertise.simpleprp.model.Customer;

public interface CustomerService {

    /**
     * Get an Customer by id.
     * 
     * @param id
     * @return Customer
     */
    Customer get(Long id);

    /**
     * Deletes a Customer by id.
     * 
     * @param id
     *            ID of the Customer
     */
    void remove(Long id);

    /**
     * Save a new Customer or update an existing Customer.
     * 
     * @param Customer
     *            Customer to change or create
     * @return New or updated Customer
     */
    Customer save(Customer customer);

    /**
     * Get an Customer by name.
     * 
     * @param name
     *            Name of the Customer
     * @return Customer object
     */
    Customer getByName(String name);

    /**
     * Checks the existence of a Customer by id
     * 
     * @param id
     *            Customer ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Finds all Customers
     * 
     * @return List of Customer objects
     */
    List<Customer> findAll();
}
