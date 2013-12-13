package de.webertise.simpleprp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import de.webertise.simpleprp.service.UserService;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    // *******************************************************************
    // * Properties
    // *******************************************************************

    @Autowired
    private UserService userService;

    // *******************************************************************
    // * Constructor
    // *******************************************************************

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        de.webertise.simpleprp.model.User user = userService.getByUsername(username);
        logger.debug("CustomUserDetailsService - loadUserByUsername: with username = " + username);
        return new CustomUserDetails(user);
    }

    // *******************************************************************
    // * Getter & Setter
    // *******************************************************************

    /**
     * @return the userService
     */
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
