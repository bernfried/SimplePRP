package de.webertise.simpleprp.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.webertise.simpleprp.model.User;

/**
 * Dao Object for the User Entity.
 * 
 * @author BernfriedHowe
 *
 */
public interface UserDao extends CrudRepository<User, Long> {

	/**
	 * Retrieves a user based on the login
	 * 
	 * @param login Login of the user
	 * @return User
	 */
	User getByLogin(String login);
	
	/**
	 * Retrieves a user based on the email address
	 * 
	 * @param email Email Address of the user
	 * @return User
	 */
	User getByEmail(String email);
	
	/**
	 * Find all users based on enabled status (true/false)
	 * 
	 * @param enabled Current status (true/false)
	 * @return List of User objects
	 */
	List<User> findByEnabled(boolean enabled);
	
	
	/**
	 * Find all users, whose account is expired or is not expired
	 * 
	 * @param accountNonExpired
	 * @return List of User objects
	 */
	List<User> findByAccountNonExpired(boolean accountNonExpired);
	
	/**
	 * Find all users, whose account is locked or is not locked
	 * 
	 * @param accountNonLocked
	 * @return List of User objects
	 */
	List<User> findByAccountNonLocked(boolean accountNonLocked);

	/**
	 * Find all users whose credentials are expired or not expired.
	 * 
	 * @param credentialsNonExpired
	 * @return List of User objects
	 */
	List<User> findByCredentialsNonExpired(boolean credentialsNonExpired);
	
	
}
