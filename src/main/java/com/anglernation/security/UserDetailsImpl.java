package com.anglernation.security;

import com.anglernation.models.users.User;
import com.anglernation.utils.JwtUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final JwtUtils jwtUtils;

    public UserDetailsImpl(User user, JwtUtils jwtUtils) {
        this.user = user;
        this.jwtUtils = jwtUtils;
    }

    public static UserDetailsImpl build(User user, JwtUtils jwtUtils) {
        return new UserDetailsImpl(user, jwtUtils);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> userRoles = new HashSet<>();
        userRoles.add(user.getUserName()); // using username as role for simplicity
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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

    public User getUser() {
        return user;
    }

    public String generateJwtToken() {
        return jwtUtils.generateAccessToken(user.getUserName(), 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserName())));
    }
}