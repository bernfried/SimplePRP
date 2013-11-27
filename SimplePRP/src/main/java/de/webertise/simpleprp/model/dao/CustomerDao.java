package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Customer;

public interface CustomerDao extends CrudRepository<Customer, Long> {

    /**
     * Get a Customer by name
     * 
     * @param name
     *            Name of the Customer
     * @return Customer
     */
    Customer getByName(String name);

}
