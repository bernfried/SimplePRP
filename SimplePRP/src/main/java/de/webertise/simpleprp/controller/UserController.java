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

import de.webertise.simpleprp.model.User;
import de.webertise.simpleprp.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * Get a user by ID
	 * 
	 * @param userId
	 *            Id of the user
	 * @return User
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getById(@PathVariable Long userId) {
		logger.info("UserController - getById: userId = '" + userId + "'");

		// get the user by id
		User user = userService.get(userId);

		// return http status 404 (not found)
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		// return the user found
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Get all users
	 * 
	 * @return List of Users
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<User> getAll() {
		logger.info("UserController - getAll: reached");

		// get the user by id
		List<User> users = userService.findUsers();

		// return user object as json
		return users;
	}

	/**
	 * Delete a user by id
	 * 
	 * @param userId
	 *            Id of the user to be deleted
	 * @return Response with correct http status code
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> delete(@PathVariable Long userId) {
		logger.info("UserController - delete: userId = '" + userId + "'");

		// check if id exists
		if (userId != null && !userService.exists(userId)) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		// get user object
		User deletedUser = userService.get(userId);

		// delete user by id
		userService.remove(userId);

		// check if delete was successful
		if (userService.exists(userId)) {
			return new ResponseEntity<User>(deletedUser, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
	}

	/**
	 * Create a new user, but check if email or login is not is use already.
	 * 
	 * @param user
	 *            New user object
	 * @param builder
	 *            Uri builder
	 * @return Newly created user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> create(@RequestBody User user, UriComponentsBuilder builder) {
		logger.info("UserController - create: reached");

		// check if an user with that email and login name already exists
		User existsUser = userService.getByEmail(user.getEmail());
		if (existsUser != null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		} else {
			existsUser = userService.getByLogin(user.getLogin());
			if (existsUser != null) {
				return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
			}
		}

		// create user
		User newUser = userService.save(user);

		// set http header (location)
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri());

		// return new user
		return new ResponseEntity<User>(newUser, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestBody User user, UriComponentsBuilder builder) {
		logger.info("UserController - update: reached");

		// check if an user with that email and login name already exists
		User existsUser = userService.getByEmail(user.getEmail());
		if (existsUser == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		// TODO: add all relevant properties
		existsUser.setFirstName(user.getFirstName());

		// set http header (location)
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(existsUser.getId()).toUri());

		// return new user
		return new ResponseEntity<User>(existsUser, headers, HttpStatus.CREATED);
	}

}
