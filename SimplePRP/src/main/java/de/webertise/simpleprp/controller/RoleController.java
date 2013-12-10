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
import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.service.RoleService;

/**
 * Handles requests for roles.
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * Get a role by ID
     * 
     * @param roleId
     *            Id of the role
     * @return Role
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        logger.info("RoleController - getRoleById: roleId = '" + roleId + "'");

        // get the role by id
        Role role = roleService.get(roleId);

        // return http status 404 (not found)
        if (role == null) {
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }

        // return the role found
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }

    /**
     * Get all roles
     * 
     * @return List of Roles
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<Role>> getRoles() {
        logger.info("RoleController - getRoles: reached");

        // get the role by id
        List<Role> roles = roleService.findAll();

        JaxbList<Role> jaxbList = new JaxbList<Role>(roles);

        // return role object as json / xml
        return new ResponseEntity<JaxbList<Role>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a role by id
     * 
     * @param roleId
     *            Id of the role to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<Role> deleteRoleById(@PathVariable Long roleId) throws Exception {
        logger.info("RoleController - deleteRoleById: roleId = '" + roleId + "'");

        // check if id exists
        if (roleId != null && !roleService.exists(roleId)) {
            throw new ObjectNotFoundException("Role with id '" + roleId + "' not found.");
        }

        // get role object
        Role deletedRole = roleService.get(roleId);

        // delete role by id
        roleService.remove(roleId);

        // check if delete was successful
        if (!roleService.exists(roleId)) {
            throw new ObjectDeletionFailedException("Role with id '" + roleId + "' could not be deleted.", deletedRole);
        }

        return new ResponseEntity<Role>(deletedRole, HttpStatus.OK);
    }

    /**
     * Create a new role
     * 
     * @param role
     *            New role object
     * @param builder
     *            Uri builder
     * @return Newly created role
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<Role> createRole(@RequestBody Role role, UriComponentsBuilder builder) throws Exception {
        logger.info("RoleController - createRole: reached");

        // check if a role with that name already exists
        Role existsRole = roleService.getByName(role.getName());
        if (existsRole != null) {
            throw new ObjectExistsAlreadyException("Role with name '" + role.getName() + "' exists already.", existsRole);
        }

        // create role
        Role newRole = roleService.save(role);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/roles/{id}").buildAndExpand(newRole.getId()).toUri());

        // return new role
        return new ResponseEntity<Role>(newRole, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing role.
     * 
     * @param role
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated role object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<Role> updateRole(@RequestBody Role role, UriComponentsBuilder builder) throws Exception {
        logger.info("RoleController - updateRole: reached");

        // check if a role with that name exists
        Role existsRole = roleService.getByName(role.getName());
        if (existsRole == null) {
            throw new ObjectNotFoundException("Role with name '" + role.getName() + "' not found.");
        }

        // Update all changeable role properties
        existsRole.setName(role.getName());
        existsRole.setChangedAt(role.getChangedAt());
        existsRole.setChangedBy(role.getChangedBy());

        Role updatedRole = roleService.save(existsRole);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/roles/{id}").buildAndExpand(updatedRole.getId()).toUri());

        // return updated role
        return new ResponseEntity<Role>(updatedRole, headers, HttpStatus.OK);
    }

}
