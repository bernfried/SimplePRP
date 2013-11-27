package de.webertise.simpleprp.service;

import de.webertise.simpleprp.model.Currency;

public interface CurrencyService {

    /**
     * Get an Currency by id.
     * 
     * @param id
     * @return Currency
     */
    Currency get(Long id);

    /**
     * Deletes a Currency by id.
     * 
     * @param id
     *            ID of the Currency
     */
    void remove(Long id);

    /**
     * Save a new Currency or update an existing Currency.
     * 
     * @param Currency
     *            Currency to change or create
     * @return New or updated Currency
     */
    Currency save(Currency currency);

    /**
     * Get an Currency by name.
     * 
     * @param name
     *            Name of the Currency
     * @return Currency object
     */
    Currency getByName(String name);

}
