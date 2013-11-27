package de.webertise.simpleprp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.ResourceReservation;
import de.webertise.simpleprp.model.dao.ResourceReservationDao;
import de.webertise.simpleprp.service.ResourceReservationService;

@Service("resourceReservationService")
@Transactional
public class ResourceReservationServiceImpl implements ResourceReservationService {

    @Autowired
    private ResourceReservationDao resourceReservationDao;

    @Override
    public ResourceReservation get(Long id) {
        return resourceReservationDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        resourceReservationDao.delete(id);
    }

    @Override
    public ResourceReservation save(ResourceReservation resReservation) {
        return resourceReservationDao.save(resReservation);
    }

    @Override
    public ResourceReservation getByName(String name) {
        return resourceReservationDao.getByName(name);
    }

}
