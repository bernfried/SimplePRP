package de.webertise.simpleprp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Client;
import de.webertise.simpleprp.model.dao.ClientDao;
import de.webertise.simpleprp.service.ClientService;

@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public Client get(Long id) {
        return clientDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        clientDao.delete(id);
    }

    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    public Client getByName(String name) {
        return clientDao.getByName(name);
    }

    @Override
    public boolean exists(Long id) {
        return clientDao.exists(id);
    }

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientDao.findAll();
    }
}
