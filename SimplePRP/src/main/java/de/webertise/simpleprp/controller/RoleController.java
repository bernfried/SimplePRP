package de.webertise.simpleprp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.service.RoleService;
import de.webertise.simpleprp.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Get a user by ID
     * 
     * @param userId
     *            Id of the user
     * @return User
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        logger.info("UserController - getRoleById: roleId = '" + roleId + "'");

        // get the user by id
        Role role = roleService.get(roleId);

        // return http status 404 (not found)
        if (role == null) {
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }

        // return the user found
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }
}
