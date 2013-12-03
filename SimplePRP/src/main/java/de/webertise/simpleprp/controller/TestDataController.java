package de.webertise.simpleprp.controller;

import java.util.List;

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

import de.webertise.simpleprp.service.TestDataService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/testdata")
public class TestDataController {

    private static final Logger logger = LoggerFactory.getLogger(TestDataController.class);

    @Autowired
    private TestDataService tds;

    @RequestMapping(value = "/generate/{amountUsers}", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<String>> generateTestData(@PathVariable int amountUsers) {
        logger.info("TestDataController - generateTestData: amount = '" + amountUsers + "'");

        List<String> result = tds.generate(amountUsers);

        // return the user found
        return new ResponseEntity<List<String>>(result, HttpStatus.OK);
    }

}
