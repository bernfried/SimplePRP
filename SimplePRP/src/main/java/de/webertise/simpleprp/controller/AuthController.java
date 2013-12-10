package de.webertise.simpleprp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.webertise.simpleprp.exception.AuthenticationRequiredException;

/**
 * Handles requests for the authentication methods
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/authenticationRequired", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> authenticationRequired() throws Exception {
        logger.info("AuthController - authenticationRequired");
        throw new AuthenticationRequiredException("Authentication required.");
    }

    @RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> logoutSuccess() throws Exception {
        logger.info("AuthController - logoutSuccess");

        // TODO: implement logoutSuccess method

        // return return code
        return new ResponseEntity<String>("logout successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> loginSuccess() throws Exception {
        logger.info("AuthController - loginSuccess");

        // TODO: implement logoutSuccess method

        // return return code
        return new ResponseEntity<String>("login successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticationFailed", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> authenticationFailed() throws Exception {
        logger.info("AuthController - authenticationFailed");

        // TODO: implement authenticationFailed method

        // return return code
        return new ResponseEntity<String>("authentication failed", HttpStatus.OK);
    }

    @RequestMapping(value = "/invalidSession", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> invalidSession() throws Exception {
        logger.info("AuthController - invalidSession");

        // TODO: implement invalidSession method

        // return return code
        return new ResponseEntity<String>("invalid session", HttpStatus.OK);
    }

    @RequestMapping(value = "/sessionAuthenticationError", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<String> sessionAuthenticationError() throws Exception {
        logger.info("AuthController - sessionAuthenticationError");

        // TODO: implement sessionAuthenticationError method

        // return return code
        return new ResponseEntity<String>("session authentication error", HttpStatus.OK);
    }

}
