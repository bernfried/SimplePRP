package de.webertise.simpleprp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.webertise.simpleprp.model.Currency;
import de.webertise.simpleprp.model.dao.CurrencyDao;
import de.webertise.simpleprp.service.CurrencyService;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public Currency get(Long id) {
        return currencyDao.findOne(id);
    }

    @Override
    public void remove(Long id) {
        currencyDao.delete(id);
    }

    @Override
    public Currency save(Currency currency) {
        return currencyDao.save(currency);
    }

    @Override
    public Currency getByName(String name) {
        return currencyDao.getByName(name);
    }

}
