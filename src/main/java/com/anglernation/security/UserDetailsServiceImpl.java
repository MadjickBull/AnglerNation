package com.anglernation.security;

import com.anglernation.models.users.User;
import com.anglernation.services.repositories.UserRepo;
import com.anglernation.utils.JwtUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return new UserDetailsImpl(user, jwtUtils);
    }
}