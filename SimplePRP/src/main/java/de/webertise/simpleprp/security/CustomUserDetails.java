package de.webertise.simpleprp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.webertise.simpleprp.model.Role;

/**
 * UserDetails
 * 
 * @author BernfriedHowe
 */
@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

    // *******************************************************************
    // * Properties
    // *******************************************************************

    private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

    private de.webertise.simpleprp.model.User user;

    // *******************************************************************
    // * Constructor
    // *******************************************************************

    public CustomUserDetails(de.webertise.simpleprp.model.User user) {
        this.user = user;
        Set<Role> roles = user.getAuthorities();
        for (Role role : roles) {
            this.grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    // *******************************************************************
    // * Getter & Setter of inline class
    // *******************************************************************

    /**
     * @return the grantedAuthorities
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    /**
     * @param grantedAuthorities
     *            the grantedAuthorities to set
     */
    public void setGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    /**
     * @return the user
     */
    public de.webertise.simpleprp.model.User getUser() {
        return this.user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(de.webertise.simpleprp.model.User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public String getSalt() {
        return this.user.getSalt();
    }
}
