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
import de.webertise.simpleprp.model.Module;
import de.webertise.simpleprp.service.ModuleService;

/**
 * Handles requests for modules.
 */
@Controller
@RequestMapping("/modules")
public class ModuleController {

    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;

    /**
     * Get a module by ID
     * 
     * @param moduleId
     *            Id of the module
     * @return Module
     */
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Module> getModuleById(@PathVariable Long moduleId) {
        logger.info("ModuleController - getModuleById: moduleId = '" + moduleId + "'");

        // get the module by id
        Module module = moduleService.get(moduleId);

        // return http status 404 (not found)
        if (module == null) {
            return new ResponseEntity<Module>(HttpStatus.NOT_FOUND);
        }

        // return the module found
        return new ResponseEntity<Module>(module, HttpStatus.OK);
    }

    /**
     * Get all modules
     * 
     * @return List of Modules
     */
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<JaxbList<Module>> getModules() {
        logger.info("ModuleController - getModules: reached");

        // get the module by id
        List<Module> modules = moduleService.findAll();

        JaxbList<Module> jaxbList = new JaxbList<Module>(modules);

        // return module object as json / xml
        return new ResponseEntity<JaxbList<Module>>(jaxbList, HttpStatus.OK);
    }

    /**
     * Delete a module by id
     * 
     * @param moduleId
     *            Id of the module to be deleted
     * @return Response with correct http status code
     * @throws Exception
     */
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.DELETE, produces = { "application/json", "application/xml" })
    public ResponseEntity<Module> deleteModuleById(@PathVariable Long moduleId) throws Exception {
        logger.info("ModuleController - deleteModuleById: moduleId = '" + moduleId + "'");

        // check if id exists
        if (moduleId != null && !moduleService.exists(moduleId)) {
            throw new ObjectNotFoundException("Module with id '" + moduleId + "' not found.");
        }

        // get module object
        Module deletedModule = moduleService.get(moduleId);

        // delete module by id
        moduleService.remove(moduleId);

        // check if delete was successful
        if (!moduleService.exists(moduleId)) {
            throw new ObjectDeletionFailedException("Module with id '" + moduleId + "' could not be deleted.", deletedModule);
        }

        return new ResponseEntity<Module>(deletedModule, HttpStatus.OK);
    }

    /**
     * Create a new module
     * 
     * @param module
     *            New module object
     * @param builder
     *            Uri builder
     * @return Newly created module
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    public ResponseEntity<Module> createModule(@RequestBody Module module, UriComponentsBuilder builder) throws Exception {
        logger.info("ModuleController - createModule: reached");

        // check if a module with that name already exists
        Module existsModule = moduleService.getByName(module.getName());
        if (existsModule != null) {
            throw new ObjectExistsAlreadyException("Module with name '" + module.getName() + "' exists already.", existsModule);
        }

        // create module
        Module newModule = moduleService.save(module);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/modules/{id}").buildAndExpand(newModule.getId()).toUri());

        // return new module
        return new ResponseEntity<Module>(newModule, headers, HttpStatus.CREATED);
    }

    /**
     * Updates an existing module.
     * 
     * @param module
     *            Json or XML string send as body of a http request.
     * @param builder
     *            UriComponentBuilder for building the location url of the
     *            response header.
     * @return Return the updated module object
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
    public ResponseEntity<Module> updateModule(@RequestBody Module module, UriComponentsBuilder builder) throws Exception {
        logger.info("ModuleController - updateModule: reached");

        // check if a module with that name exists
        Module existsModule = moduleService.getByName(module.getName());
        if (existsModule == null) {
            throw new ObjectNotFoundException("Module with name '" + module.getName() + "' not found.");
        }

        // Update all changeable module properties
        existsModule.setName(module.getName());
        existsModule.setChangedAt(module.getChangedAt());
        existsModule.setChangedBy(module.getChangedBy());

        Module updatedModule = moduleService.save(existsModule);

        // set http header (location)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/modules/{id}").buildAndExpand(updatedModule.getId()).toUri());

        // return updated module
        return new ResponseEntity<Module>(updatedModule, headers, HttpStatus.OK);
    }

}
