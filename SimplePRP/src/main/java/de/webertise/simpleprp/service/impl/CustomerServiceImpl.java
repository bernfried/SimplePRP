package de.webertise.simpleprp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Customer;
import de.webertise.simpleprp.model.dao.CustomerDao;
import de.webertise.simpleprp.service.CustomerService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer get(Long id) {
        return customerDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        customerDao.delete(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer getByName(String name) {
        return customerDao.getByName(name);
    }

}
