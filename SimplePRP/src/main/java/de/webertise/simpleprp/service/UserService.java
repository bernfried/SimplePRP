package de.webertise.simpleprp.service;

import java.util.List;

import de.webertise.simpleprp.model.User;

public interface UserService {

    /**
     * Get an User by id.
     * 
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * Deletes a user by id.
     * 
     * @param id
     *            ID of the user
     */
    void remove(Long id);

    /**
     * Save a new user or update an existing user.
     * 
     * @param user
     *            User to change or create
     * @return New or updated user
     */
    User save(User user);

    /**
     * Get an User by login.
     * 
     * @param login
     *            Login of the user
     * @return User object
     */
    User getByLogin(String login);

    /**
     * Get an User by Email.
     * 
     * @param email
     *            Email address of the user
     * @return User object
     */
    User getByEmail(String email);

    /**
     * Find all users which are enabled or disabled.
     * 
     * @param enabled
     *            true/false
     * @return List of User objects
     */
    List<User> findByEnabled(boolean enabled);

    /**
     * Find a list of users whose account is expired or not expired.
     * 
     * @param accountNonExpired
     *            true/false
     * @return List of user objects
     */
    List<User> findByAccountNonExpired(boolean accountNonExpired);

    /**
     * Find a list of users whose account is not locked of locked.
     * 
     * @param accountNonLocked
     *            true/false
     * @return List of User objects
     */
    List<User> findByAccountNonLocked(boolean accountNonLocked);

    /**
     * Find a list of users whose credentials are expired or not expired.
     * 
     * @param credentialsNonExpired
     *            true/false
     * @return List of User objects
     */
    List<User> findByCredentialsNonExpired(boolean credentialsNonExpired);

    /**
     * Finds all Users
     * 
     * @return List of User objects
     */
    List<User> findAll();

    /**
     * Checks the existence of a user by id
     * 
     * @param id
     *            User ID
     * @return true/false
     */
    boolean exists(Long id);

    /**
     * Authenticates a user based on login and plain password.
     * 
     * @param login
     *            Login
     * @param password
     *            Password
     * @return User if successful authenticated. Otherwise null.
     */
    User authenticate(String login, String password);

}
