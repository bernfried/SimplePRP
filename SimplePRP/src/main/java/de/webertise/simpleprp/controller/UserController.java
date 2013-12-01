package de.webertise.simpleprp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Spring Framework
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

// Exceptions
import de.webertise.simpleprp.exception.ObjectDeletionFailedException;
import de.webertise.simpleprp.exception.ObjectExistsAlreadyException;
import de.webertise.simpleprp.exception.ObjectNotFoundException;
// Helper
import de.webertise.simpleprp.helper.xml.JaxbList;
// Entities of the model
import de.webertise.simpleprp.model.Client;
import de.webertise.simpleprp.model.Module;
import de.webertise.simpleprp.model.Project;
import de.webertise.simpleprp.model.ResourceReservation;
import de.webertise.simpleprp.model.ResourceRole;
import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.model.User;
// Services
import de.webertise.simpleprp.service.ClientService;
import de.webertise.simpleprp.service.ModuleService;
import de.webertise.simpleprp.service.ProjectService;
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
    private ProjectService projectService;

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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getUserById: userId = '" + userId + "'");

        // get the user by id
        User user = userService.get(userId);

        // return http status 404 (not found)
        if (user == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> deleteUserById(@PathVariable Long userId) throws Exception {
        logger.info("UserController - delete: deleteUserById = '" + userId + "'");

        // check if id exists
        if (userId != null && !userService.exists(userId)) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        }

        // get user object
        User deletedUser = userService.get(userId);

        // delete user by id
        userService.remove(userId);

        // check if delete was successful
        if (!userService.exists(userId)) {
            throw new ObjectDeletionFailedException("User with id '" + userId + "' could not be deleted.", deletedUser);
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
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - createUser: reached");

        // check if an user with that email and login name already exists
        User existsUser = userService.getByEmail(user.getEmail());
        if (existsUser != null) {
            throw new ObjectExistsAlreadyException("User with email '" + user.getEmail() + "' exists already.", existsUser);
        } else {
            existsUser = userService.getByLogin(user.getLogin());
            if (existsUser != null) {
                throw new ObjectExistsAlreadyException("User with login '" + user.getLogin() + "' exists already.", existsUser);
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
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> updateUser(@RequestBody User user, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - updateUser: reached");

        // check if an user with that email and login name already exists
        User existsUser = userService.getByEmail(user.getEmail());
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with email '" + user.getEmail() + "' not found.");
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
        return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/roles/{roleId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addRoleToUser: reached with userId '" + userId + " / roleId '" + roleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Role> roles = existsUser.getAuthorities();
            Role role = roleService.get(roleId);
            if (role == null) {
                throw new ObjectNotFoundException("Role with id '" + roleId + "' not found.");
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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/roles/{roleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - removeRoleFromUser: reached");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Role> roles = existsUser.getAuthorities();
            Role role = roleService.get(roleId);
            if (role == null) {
                throw new ObjectNotFoundException("Role with id '" + roleId + "' not found.");
            } else {
                roles.remove(role);
                existsUser.setAuthorities(roles);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all roles of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of roles.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Role>> getRolesByUserId(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getRolesByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/modules/{moduleId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addModuleToUser(@PathVariable Long userId, @PathVariable Long moduleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addModuleToUser: reached with userId '" + userId + "' / moduleId '" + moduleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Module> modules = existsUser.getModules();
            Module module = moduleService.get(moduleId);
            if (module == null) {
                throw new ObjectNotFoundException("Module with id '" + moduleId + "' not found.");
            } else {
                modules.add(module);
                existsUser.setModules(modules);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
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
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/modules/{moduleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeModuleFromUser(@PathVariable Long userId, @PathVariable Long moduleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - removeModuleFromUser: reached with userId '" + userId + "' / moduleId '" + moduleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Module> modules = existsUser.getModules();
            Module module = moduleService.get(moduleId);
            if (module == null) {
                throw new ObjectNotFoundException("Module with id '" + moduleId + "' not found.");
            } else {
                modules.remove(module);
                existsUser.setModules(modules);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all modules of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of modules.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/modules", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Module>> getModulesByUserId(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getModulesByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
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

    /**
     * Adds an existing client to an existing user.
     * 
     * @param userId
     *            Id of the user
     * @param clientId
     *            Id of the client
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of clients.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/clients/{clientId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addClientToUser(@PathVariable Long userId, @PathVariable Long clientId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addClientToUser: reached with userId '" + userId + "' / clientId '" + clientId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Client> clients = existsUser.getClients();
            Client client = clientService.get(clientId);
            if (client == null) {
                throw new ObjectNotFoundException("Client with id '" + clientId + "' not found.");
            } else {
                clients.add(client);
                existsUser.setClients(clients);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Removes an existing client by id from an existing user by id.
     * 
     * @param userId
     *            Id of the user
     * @param clientId
     *            Id of the client to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of clients
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/clients/{clientId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeClientFromUser(@PathVariable Long userId, @PathVariable Long clientId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - removeClientFromUser: reached with userId '" + userId + "' / clientId '" + clientId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Client> clients = existsUser.getClients();
            Client client = clientService.get(clientId);
            if (client == null) {
                throw new ObjectNotFoundException("Client with id '" + clientId + "' not found.");
            } else {
                clients.remove(client);
                existsUser.setClients(clients);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all clients of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of clients.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/clients", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Client>> getClientsByUserId(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getClientsByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Client> clients = existsUser.getClients();
            List<Client> clientList = new ArrayList<Client>();
            for (Client client : clients) {
                clientList.add(client);
            }
            JaxbList<Client> jaxbList = new JaxbList<Client>(clientList);
            // return list of clients
            return new ResponseEntity<JaxbList<Client>>(jaxbList, HttpStatus.OK);
        }
    }

    /**
     * Adds an existing resourceRole to an existing user.
     * 
     * @param userId
     *            Id of the user
     * @param resourceRoleId
     *            Id of the resourceRole
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of resourceRoles.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceRoles/{resourceRoleId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addResourceRoleToUser(@PathVariable Long userId, @PathVariable Long resourceRoleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addResourceRoleToUser: reached with userId '" + userId + "' / resourceRoleId '" + resourceRoleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceRole> resourceRoles = existsUser.getResourceRoles();
            ResourceRole resourceRole = resourceRoleService.get(resourceRoleId);
            if (resourceRole == null) {
                throw new ObjectNotFoundException("Resource Role with id '" + resourceRoleId + "' not found.");
            } else {
                resourceRoles.add(resourceRole);
                existsUser.setResourceRoles(resourceRoles);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Removes an existing resourceRole by id from an existing user by id.
     * 
     * @param userId
     *            Id of the user
     * @param resourceRoleId
     *            Id of the resourceRole to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of resourceRoles
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceRoles/{resourceRoleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeResourceRoleFromUser(@PathVariable Long userId, @PathVariable Long resourceRoleId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - removeResourceRoleFromUser: reached with userId '" + userId + "' / resourceRoleId '" + resourceRoleId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceRole> resourceRoles = existsUser.getResourceRoles();
            ResourceRole resourceRole = resourceRoleService.get(resourceRoleId);
            if (resourceRole == null) {
                throw new ObjectNotFoundException("Resource Role with id '" + resourceRoleId + "' not found.");
            } else {
                resourceRoles.remove(resourceRole);
                existsUser.setResourceRoles(resourceRoles);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all resourceRoles of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of resourceRoles.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceRoles", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<ResourceRole>> getResourceRolesByUserId(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getResourceRolesByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceRole> resourceRoles = existsUser.getResourceRoles();
            List<ResourceRole> resourceRoleList = new ArrayList<ResourceRole>();
            for (ResourceRole resourceRole : resourceRoles) {
                resourceRoleList.add(resourceRole);
            }
            JaxbList<ResourceRole> jaxbList = new JaxbList<ResourceRole>(resourceRoleList);
            // return list of resourceRoles
            return new ResponseEntity<JaxbList<ResourceRole>>(jaxbList, HttpStatus.OK);
        }
    }

    /**
     * Adds an existing project to an existing user based on the specified
     * relationship type (admin, prjmgr, member).
     * 
     * @param userId
     *            Id of the user
     * @param relType
     *            Type of the user/project relationship. Can be member, prjmgr
     * @param projectId
     *            Id of the project
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of projects.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/projects/{relType}/{projectId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addProjectWithRelationTypeToUser(@PathVariable Long userId, @PathVariable String relType, @PathVariable Long projectId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addProjectWithRelationTypeToUser: reached with userId '" + userId + "' / relType '" + relType + "' / projectId ' " + projectId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Project> projects = null;
            if (relType.equals(User.RELATION_TYPE_ADMIN)) {
                projects = existsUser.getProjectsAsAdmin();
            } else if (relType.equals(User.RELATION_TYPE_PRJMGR)) {
                projects = existsUser.getProjectsAsPrjMgr();
            } else if (relType.equals(User.RELATION_TYPE_MEMBER)) {
                projects = existsUser.getProjectsAsMember();
            } else {
                throw new ObjectNotFoundException("Relationship Type '" + relType + "' wrong. Allowed values are member, prjmgr, admin.");
            }

            Project project = projectService.get(projectId);
            if (project == null) {
                throw new ObjectNotFoundException("Project with id '" + projectId + "' not found.");
            } else {
                projects.add(project);

                if (relType.equals(User.RELATION_TYPE_ADMIN)) {
                    existsUser.setProjectsAsAdmin(projects);
                } else if (relType.equals(User.RELATION_TYPE_PRJMGR)) {
                    existsUser.setProjectsAsPrjMgr(projects);
                } else if (relType.equals(User.RELATION_TYPE_MEMBER)) {
                    existsUser.setProjectsAsMember(projects);
                } else {
                    throw new ObjectNotFoundException("Relationship Type '" + relType + "' wrong. Allowed values are member, prjmgr, admin.");
                }
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Removes an existing project of a certain relationship type (admin,
     * member, prjmgr) by projectId from an existing user by userId.
     * 
     * @param userId
     *            Id of the user
     * @param relType
     *            Relationship type (admin, member, prjmgr)
     * @param projectId
     *            Id of the project to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of resourceRoles
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/projects/{relType}/{projectId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeProjectWithRelationTypeToUser(@PathVariable Long userId, @PathVariable String relType, @PathVariable Long projectId, UriComponentsBuilder builder)
            throws Exception {
        logger.info("UserController - removeProjectWithRelationTypeToUser: reached with userId '" + userId + "' / projectId '" + projectId + "' / relType '" + relType + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {

            Set<Project> projects = null;
            if (relType.equals(User.RELATION_TYPE_ADMIN)) {
                projects = existsUser.getProjectsAsAdmin();
            } else if (relType.equals(User.RELATION_TYPE_PRJMGR)) {
                projects = existsUser.getProjectsAsPrjMgr();
            } else if (relType.equals(User.RELATION_TYPE_MEMBER)) {
                projects = existsUser.getProjectsAsMember();
            } else {
                throw new ObjectNotFoundException("Relationship Type '" + relType + "' wrong. Allowed values are member, prjmgr, admin.");
            }

            // get project for given id
            Project project = projectService.get(projectId);
            if (project == null) {
                throw new ObjectNotFoundException("Project with id '" + projectId + "' not found.");
            } else {
                projects.remove(project);

                if (relType.equals(User.RELATION_TYPE_ADMIN)) {
                    existsUser.setProjectsAsAdmin(projects);
                } else if (relType.equals(User.RELATION_TYPE_PRJMGR)) {
                    existsUser.setProjectsAsPrjMgr(projects);
                } else if (relType.equals(User.RELATION_TYPE_MEMBER)) {
                    existsUser.setProjectsAsMember(projects);
                } else {
                    throw new ObjectNotFoundException("Relationship Type '" + relType + "' wrong. Allowed values are member, prjmgr, admin.");
                }

                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all projects where the user with the given userId is a member, prjmgr
     * or admin.
     * 
     * @param userId
     *            Id of the user
     * @param relType
     *            Type of the user/project relationship. Can be member, prjmgr
     *            or admin.
     * @return List of resourceRoles.
     * @throws IOException
     */
    @RequestMapping(value = "/{userId}/projects/{relType}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<Project>> getProjectsByUserIdAndRelationType(@PathVariable Long userId, @PathVariable String relType) throws Exception {
        logger.info("UserController - getProjectsByUserIdAndRelationType: reached with userId: '" + userId + "' / relType '" + relType + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<Project> projects = null;
            if (relType.equals(User.RELATION_TYPE_ADMIN)) {
                projects = existsUser.getProjectsAsAdmin();
            } else if (relType.equals(User.RELATION_TYPE_PRJMGR)) {
                projects = existsUser.getProjectsAsPrjMgr();
            } else if (relType.equals(User.RELATION_TYPE_MEMBER)) {
                projects = existsUser.getProjectsAsMember();
            } else {
                throw new ObjectNotFoundException("Relationship Type '" + relType + "' wrong. Allowed values are member, prjmgr, admin.");
            }
            List<Project> projectList = new ArrayList<Project>();
            for (Project project : projects) {
                projectList.add(project);
            }
            JaxbList<Project> jaxbList = new JaxbList<Project>(projectList);
            // return list of projects
            return new ResponseEntity<JaxbList<Project>>(jaxbList, HttpStatus.OK);
        }
    }

    /**
     * Adds an existing resourceReservation to an existing user.
     * 
     * @param userId
     *            Id of the user
     * @param resourceReservationId
     *            Id of the resourceReservation
     * @param builder
     *            Uri Components Builder
     * @return User Object with updated list of resourceReservations.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceReservations/{resourceReservationId}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> addResourceReservationToUser(@PathVariable Long userId, @PathVariable Long resourceReservationId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - addResourceReservationToUser: reached with userId '" + userId + "' / resourceReservationId '" + resourceReservationId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceReservation> resourceReservations = existsUser.getResourceReservations();
            ResourceReservation resourceReservation = resourceReservationService.get(resourceReservationId);
            if (resourceReservation == null) {
                throw new ObjectNotFoundException("Resource Reservation with id '" + resourceReservationId + "' not found.");
            } else {
                resourceReservations.add(resourceReservation);
                existsUser.setResourceReservations(resourceReservations);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Removes an existing resourceReservation by id from an existing user by
     * id.
     * 
     * @param userId
     *            Id of the user
     * @param resourceReservationId
     *            Id of the resourceReservation to be removed.
     * @param builder
     *            Uri Component Builder
     * @return User with updated list of resourceReservations
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceReservations/{resourceReservationId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<User> removeResourceReservationFromUser(@PathVariable Long userId, @PathVariable Long resourceReservationId, UriComponentsBuilder builder) throws Exception {
        logger.info("UserController - removeResourceReservationFromUser: reached with userId '" + userId + "' / resourceReservationId '" + resourceReservationId + "'");

        // check if an user with id exists
        User updatedUser = null;
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceReservation> resourceReservations = existsUser.getResourceReservations();
            ResourceReservation resourceReservation = resourceReservationService.get(resourceReservationId);
            if (resourceReservation == null) {
                throw new ObjectNotFoundException("Resource Reservation with id '" + resourceReservationId + "' not found.");
            } else {
                resourceReservations.remove(resourceReservation);
                existsUser.setResourceReservations(resourceReservations);
                updatedUser = userService.save(existsUser);

                // set http header (location)
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/users/{id}").buildAndExpand(updatedUser.getId()).toUri());

                // return new user
                return new ResponseEntity<User>(updatedUser, headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Get all resourceReservations of an user by userId
     * 
     * @param userId
     *            Id of the user
     * @return List of resourceReservations.
     * @throws Exception
     */
    @RequestMapping(value = "/{userId}/resourceReservations", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public ResponseEntity<JaxbList<ResourceReservation>> getResourceReservationsByUserId(@PathVariable Long userId) throws Exception {
        logger.info("UserController - getResourceReservationsByUserId: reached with userId: '" + userId + "'");

        // check if an user with id exists
        User existsUser = userService.get(userId);
        if (existsUser == null) {
            throw new ObjectNotFoundException("User with id '" + userId + "' not found.");
        } else {
            Set<ResourceReservation> resourceReservations = existsUser.getResourceReservations();
            List<ResourceReservation> resourceReservationList = new ArrayList<ResourceReservation>();
            for (ResourceReservation resourceReservation : resourceReservations) {
                resourceReservationList.add(resourceReservation);
            }
            JaxbList<ResourceReservation> jaxbList = new JaxbList<ResourceReservation>(resourceReservationList);
            // return list of resourceReservations
            return new ResponseEntity<JaxbList<ResourceReservation>>(jaxbList, HttpStatus.OK);
        }
    }

}
