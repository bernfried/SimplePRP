package de.webertise.simpleprp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Project;
import de.webertise.simpleprp.model.dao.ProjectDao;
import de.webertise.simpleprp.service.ProjectService;

@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project get(Long id) {
        return projectDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        projectDao.delete(id);
    }

    @Override
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project getByName(String name) {
        return projectDao.getByName(name);
    }

    @Override
    public boolean exists(Long id) {
        return projectDao.exists(id);
    }

    @Override
    public List<Project> findAll() {
        return (List<Project>) projectDao.findAll();
    }

}
