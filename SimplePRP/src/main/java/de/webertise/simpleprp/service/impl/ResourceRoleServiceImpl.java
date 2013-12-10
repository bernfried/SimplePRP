package de.webertise.simpleprp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.ResourceRole;
import de.webertise.simpleprp.model.dao.ResourceRoleDao;
import de.webertise.simpleprp.service.ResourceRoleService;

@Service("resourceRoleService")
@Transactional
public class ResourceRoleServiceImpl implements ResourceRoleService {

    @Autowired
    private ResourceRoleDao resourceRoleDao;

    @Override
    public ResourceRole get(Long id) {
        return resourceRoleDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        resourceRoleDao.delete(id);
    }

    @Override
    public ResourceRole save(ResourceRole resRole) {
        return resourceRoleDao.save(resRole);
    }

    @Override
    public ResourceRole getByName(String name) {
        return resourceRoleDao.getByName(name);
    }

    @Override
    public boolean exists(Long id) {
        return resourceRoleDao.exists(id);
    }

    @Override
    public List<ResourceRole> findAll() {
        return (List<ResourceRole>) resourceRoleDao.findAll();
    }
}
