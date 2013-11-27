package de.webertise.simpleprp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Module;
import de.webertise.simpleprp.model.dao.ModuleDao;
import de.webertise.simpleprp.service.ModuleService;

@Service("moduleService")
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module get(Long id) {
        return moduleDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        moduleDao.delete(id);
    }

    @Override
    public Module save(Module module) {
        return moduleDao.save(module);
    }

    @Override
    public Module getByName(String name) {
        return moduleDao.getByName(name);
    }

}
