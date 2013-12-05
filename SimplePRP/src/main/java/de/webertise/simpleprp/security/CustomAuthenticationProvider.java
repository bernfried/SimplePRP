package de.webertise.simpleprp.security;

import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.model.User;
import de.webertise.simpleprp.service.UserService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserService userService;

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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // get login and password from request using the authentication object
        String login = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        logger.debug("authenticate(): with login='" + login + "' and password='" + password + "'");

        User user = userService.authenticate(login, password);

        if (user != null) {
            Set<Role> roles = user.getAuthorities();
            LinkedList<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), authorities);
        }
        return null;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (authentication.equals(UsernamePasswordAuthenticationToken.class));
    }

}
