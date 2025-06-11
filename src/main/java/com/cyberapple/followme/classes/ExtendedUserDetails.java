package com.cyberapple.followme.classes;

import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;

import com.cyberapple.followme.entities.User;

import org.springframework.security.core.GrantedAuthority;

public class ExtendedUserDetails implements UserDetails {
    private User user; 

    private Collection<? extends GrantedAuthority> authorities;

    public ExtendedUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
