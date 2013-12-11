package de.webertise.simpleprp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import de.webertise.simpleprp.exception.ObjectDeletionFailedException;
import de.webertise.simpleprp.exception.ObjectExistsAlreadyException;
import de.webertise.simpleprp.exception.ObjectNotFoundException;
import de.webertise.simpleprp.helper.xml.JaxbList;
import de.webertise.simpleprp.model.Currency;
import de.webertise.simpleprp.service.CurrencyService;

/**
 * Handles requests for currencies.
 */
@Controller
@RequestMapping("/currencies")
public class CurrencyController {

	private static final Logger logger = LoggerFactory
			.getLogger(CurrencyController.class);

	@Autowired
	private CurrencyService currencyService;

	/**
	 * Get a currency by ID
	 * 
	 * @param currencyId
	 *            Id of the currency
	 * @return Currency
	 */
	@RequestMapping(value = "/{currencyId}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public ResponseEntity<Currency> getCurrencyById(
			@PathVariable Long currencyId) {
		logger.info("CurrencyController - getCurrencyById: currencyId = '"
				+ currencyId + "'");

		// get the currency by id
		Currency currency = currencyService.get(currencyId);

		// return http status 404 (not found)
		if (currency == null) {
			return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND);
		}

		// return the currency found
		return new ResponseEntity<Currency>(currency, HttpStatus.OK);
	}

	/**
	 * Get all currencies
	 * 
	 * @return List of Currencies
	 */
	@RequestMapping(method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ResponseEntity<JaxbList<Currency>> getCurrencies() {
		logger.info("CurrencyController - getCurrencies: reached");

		// get the currency by id
		List<Currency> currencies = currencyService.findAll();

		JaxbList<Currency> jaxbList = new JaxbList<Currency>(currencies);

		// return currency object as json / xml
		return new ResponseEntity<JaxbList<Currency>>(jaxbList, HttpStatus.OK);
	}

	/**
	 * Delete a currency by id
	 * 
	 * @param currencyId
	 *            Id of the currency to be deleted
	 * @return Response with correct http status code
	 * @throws Exception
	 */
	@RequestMapping(value = "/{currencyId}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<Currency> deleteCurrencyById(
			@PathVariable Long currencyId) throws Exception {
		logger.info("CurrencyController - deleteCurrencyById: currencyId = '"
				+ currencyId + "'");

		// check if id exists
		if (currencyId != null && !currencyService.exists(currencyId)) {
			throw new ObjectNotFoundException("Currency with id '" + currencyId
					+ "' not found.");
		}

		// get currency object
		Currency deletedCurrency = currencyService.get(currencyId);

		// delete currency by id
		currencyService.remove(currencyId);

		// check if delete was successful
		if (!currencyService.exists(currencyId)) {
			throw new ObjectDeletionFailedException("Currency with id '"
					+ currencyId + "' could not be deleted.", deletedCurrency);
		}

		return new ResponseEntity<Currency>(deletedCurrency, HttpStatus.OK);
	}

	/**
	 * Create a new currency
	 * 
	 * @param currency
	 *            New currency object
	 * @param builder
	 *            Uri builder
	 * @return Newly created currency
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<Currency> createCurrency(
			@RequestBody Currency currency, UriComponentsBuilder builder)
			throws Exception {
		logger.info("CurrencyController - createCurrency: reached");

		// check if a currency with that name already exists
		Currency existsCurrency = currencyService.getByName(currency.getName());
		if (existsCurrency != null) {
			throw new ObjectExistsAlreadyException("Currency with name '"
					+ currency.getName() + "' exists already.", existsCurrency);
		}

		// create currency
		Currency newCurrency = currencyService.save(currency);

		// set http header (location)
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/currencies/{id}")
				.buildAndExpand(newCurrency.getId()).toUri());

		// return new currency
		return new ResponseEntity<Currency>(newCurrency, headers,
				HttpStatus.CREATED);
	}

	/**
	 * Updates an existing currency.
	 * 
	 * @param currency
	 *            Json or XML string send as body of a http request.
	 * @param builder
	 *            UriComponentBuilder for building the location url of the
	 *            response header.
	 * @return Return the updated currency object
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<Currency> updateCurrency(
			@RequestBody Currency currency, UriComponentsBuilder builder)
			throws Exception {
		logger.info("CurrencyController - updateCurrency: reached");

		// check if a currency with that name exists
		Currency existsCurrency = currencyService.getByName(currency.getName());
		if (existsCurrency == null) {
			throw new ObjectNotFoundException("Currency with name '"
					+ currency.getName() + "' not found.");
		}

		// Update all changeable currency properties
		existsCurrency.setName(currency.getName());
		existsCurrency.setChangedAt(currency.getChangedAt());
		existsCurrency.setChangedBy(currency.getChangedBy());

		Currency updatedCurrency = currencyService.save(existsCurrency);

		// set http header (location)
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/currencies/{id}")
				.buildAndExpand(updatedCurrency.getId()).toUri());

		// return updated currency
		return new ResponseEntity<Currency>(updatedCurrency, headers,
				HttpStatus.OK);
	}

}
