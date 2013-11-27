package de.webertise.simpleprp.model.dao;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.Currency;

public interface CurrencyDao extends CrudRepository<Currency, Long> {

    /**
     * Get a Currency by name
     * 
     * @param name
     *            Name of the Currency
     * @return Currency
     */
    Currency getByName(String name);

}
