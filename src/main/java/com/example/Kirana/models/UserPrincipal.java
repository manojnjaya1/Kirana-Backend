package com.example.Kirana.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a user principal in the security context.
 * This class implements the UserDetails interface to provide user-specific information
 * such as authorities, password, and username for authentication and authorization.
 */
public class UserPrincipal implements UserDetails {

    private final Users user;

    /**
     * Constructs a UserPrincipal object from a Users instance.
     *
     * @param user the Users instance to be used for authentication
     */
    public UserPrincipal(Users user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     * In this implementation, the user has a default role of 'USER'.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the user's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     * In this implementation, the account is considered non-expired by default.
     *
     * @return true if the user's account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked or unlocked.
     * In this implementation, the account is considered unlocked by default.
     *
     * @return true if the user's account is non-locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * In this implementation, the credentials are considered non-expired by default.
     *
     * @return true if the user's credentials are non-expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * In this implementation, the user is considered enabled by default.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
