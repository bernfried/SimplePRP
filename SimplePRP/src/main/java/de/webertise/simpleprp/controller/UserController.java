package de.webertise.simpleprp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import de.webertise.simpleprp.helper.xml.JaxbList;
import de.webertise.simpleprp.model.Module;
import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.model.User;
import de.webertise.simpleprp.service.ClientService;
import de.webertise.simpleprp.service.ModuleService;
import de.webertise.simpleprp.service.ResourceReservationService;
import de.webertise.simpleprp.service.ResourceRoleService;
import de.webertise.simpleprp.service.RoleService;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ResourceRoleService resourceRoleService;

    @Autowired
    private ResourceReservationService resourceReservationService;

    /**
     * Get a user by ID
     * 
     * @param userId
     *            Id of the user
     * @return User
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        logger.info("UserController - getUserById: userId = '" + userId + "'");

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
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<User>> getUsers() {
        logger.info("UserController - getAllUsers: reached");

        // get the user by id
        List<User> users = userService.findUsers();

        JaxbList<User> jaxbList = new JaxbList<User>(users);

        // return user object as json / xml
        return new ResponseEntity<JaxbList<User>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a user by id
     * 
     * @param userId
     *            Id of the user to be deleted
     * @return Response with correct http status code
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> deleteUserById(@PathVariable Long userId) {
        logger.info("UserController - delete: deleteUserById = '" + userId + "'");

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
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        logger.info("UserController - createUser: reached");

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

    /**
     * Updates an existing user.
     * 
     * @param user
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated user object
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> updateUser(@RequestBody User user, UriComponentsBuilder builder) {
        logger.info("UserController - updateUser: reached");

        // check if an user with that email and login name already exists
        User existsUser = userService.getByEmail(user.getEmail());
        if (existsUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        // Update all changeable user properties
        existsUser.setFirstName(user.getFirstName());
        existsUser.setLastName(user.getLastName());
        existsUser.setChangedAt(user.getChangedAt());
        existsUser.setChangedBy(user.getChangedBy());

        User updatedUser = userService.save(existsUser);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

        // return new user
        return new ResponseEntity<User>(updatedUser, headers, HttpStatus.CREATED);
    }

    /**
     * Adds an existing role to an existing user.
     * 
     * @param userId
     *            Id of the user
     * @param roleId
     *            Id of the role
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of roles.
     */
    @RequestMapping(value = "/{userId}/roles/{roleId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId, UriComponentsBuilder builder) {
        logger.info("UserController - addRoleToUser: reached with userId '" + userId + " / roleId '" + roleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            Set<Role> roles = existsUser.getAuthorities();
            Role role = roleService.get(roleId);
            if (role == null) {
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            } else {
                roles.add(role);
                existsUser.setAuthorities(roles);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.CREATED);
            }
        }
    }

    /**
     * Removes an existing role by id from an existing user by id.
     * 
     * @param userId
     *            Id of the user
     * @param roleId
     *            Id of the role to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of roles
     */
    @RequestMapping(value = "/{userId}/roles/{roleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId, UriComponentsBuilder builder) {
        logger.info("UserController - removeRoleFromUser: reached");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            Set<Role> roles = existsUser.getAuthorities();
            Role role = roleService.get(roleId);
            if (role == null) {
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            } else {
                roles.remove(role);
                existsUser.setAuthorities(roles);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.CREATED);
            }
        }
    }

    /**
     * Get all roles of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of roles.
     */
    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Role>> getRolesByUserId(@PathVariable Long userId) {
        logger.info("UserController - getRolesByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<JaxbList<Role>>(HttpStatus.NOT_FOUND);
        } else {
            Set<Role> roles = existsUser.getAuthorities();
            List<Role> roleList = new ArrayList<Role>();
            for (Role role : roles) {
                roleList.add(role);
            }
            JaxbList<Role> jaxbList = new JaxbList<Role>(roleList);
            // return list of roles
            return new ResponseEntity<JaxbList<Role>>(jaxbList, HttpStatus.OK);
        }
    }

    /**
     * Adds an existing module to an existing user.
     * 
     * @param userId
     *            Id of the user
     * @param moduleId
     *            Id of the module
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of modules.
     */
    @RequestMapping(value = "/{userId}/modules/{moduleId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addModuleToUser(@PathVariable Long userId, @PathVariable Long moduleId, UriComponentsBuilder builder) {
        logger.info("UserController - addModuleToUser: reached with userId '" + userId + "' / moduleId '" + moduleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            Set<Module> modules = existsUser.getModules();
            Module module = moduleService.get(moduleId);
            if (module == null) {
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            } else {
                modules.add(module);
                existsUser.setModules(modules);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.CREATED);
            }
        }
    }

    /**
     * Removes an existing module by id from an existing user by id.
     * 
     * @param userId
     *            Id of the user
     * @param moduleId
     *            Id of the module to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of modules
     */
    @RequestMapping(value = "/{userId}/modules/{moduleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeModuleFromUser(@PathVariable Long userId, @PathVariable Long moduleId, UriComponentsBuilder builder) {
        logger.info("UserController - removeModuleFromUser: reached with userId '" + userId + "' / moduleId '" + moduleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            Set<Module> modules = existsUser.getModules();
            Module module = moduleService.get(moduleId);
            if (module == null) {
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            } else {
                modules.remove(module);
                existsUser.setModules(modules);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.CREATED);
            }
        }
    }

    /**
     * Get all modules of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of modules.
     */
    @RequestMapping(value = "/{userId}/modules", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Module>> getModulesByUserId(@PathVariable Long userId) {
        logger.info("UserController - getModulesByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            return new ResponseEntity<JaxbList<Module>>(HttpStatus.NOT_FOUND);
        } else {
            Set<Module> modules = existsUser.getModules();
            List<Module> moduleList = new ArrayList<Module>();
            for (Module module : modules) {
                moduleList.add(module);
            }
            JaxbList<Module> jaxbList = new JaxbList<Module>(moduleList);
            // return list of modules
            return new ResponseEntity<JaxbList<Module>>(jaxbList, HttpStatus.OK);
        }
    }

}
