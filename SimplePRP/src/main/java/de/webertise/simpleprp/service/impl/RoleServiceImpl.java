package de.webertise.simpleprp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.model.dao.RoleDao;
import de.webertise.simpleprp.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role get(Long id) {
        return roleDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        roleDao.delete(id);
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    public boolean exists(Long id) {
        return roleDao.exists(id);
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleDao.findAll();
    }

}
