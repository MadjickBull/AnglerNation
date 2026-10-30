package com.anglernation.service;

import com.anglernation.models.users.User;
import com.anglernation.dtos.UserDto;
import com.anglernation.security.RefreshToken;
import com.anglernation.security.RefreshTokenRepo;
import com.anglernation.services.repositories.UserRepo;
import com.anglernation.dto.auth.RegisterRequest;
import com.anglernation.exceptions.EmailAlreadyExistsException;
import com.anglernation.exceptions.UsernameAlreadyExistsException;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    private final UserRepo userRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, RefreshTokenRepo refreshTokenRepo) {
        this.userRepo = userRepo;
        this.refreshTokenRepo = refreshTokenRepo;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public UserDto register(RegisterRequest request) {
        if (userRepo.findByEmail(request.email()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already registered");
            }
            if (userRepo.findByUserName(request.username()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username already taken");
            }

        User user = new User();
        user.setEmail(request.email());
        user.setUserName(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());

        User saved = userRepo.save(user);
        return mapToDto(saved);
    }

    public void storeRefreshToken(String username, String token) {
        String tokenHash = hashToken(token);
        RefreshToken rt = new RefreshToken();
        rt.setTokenHash(tokenHash);
        rt.setUsername(username);
        rt.setExpiresAt(Instant.now().plusSeconds(30 * 24 * 60 * 60));
        rt.setCreatedAt(Instant.now());
        refreshTokenRepo.save(rt);
    }

    public boolean validateRefreshToken(String username, String token) {
        String tokenHash = hashToken(token);
        return refreshTokenRepo.findByUsernameAndTokenHash(username, tokenHash)
            .map(rt -> !rt.isRevoked() && rt.getExpiresAt().isAfter(Instant.now()))
            .orElse(false);
    }

    public void revokeRefreshToken(String username, String token) {
        String tokenHash = hashToken(token);
        refreshTokenRepo.findByUsernameAndTokenHash(username, tokenHash)
            .ifPresent(rt -> {
                rt.setRevoked(true);
                rt.setRevokedAt(Instant.now());
                refreshTokenRepo.save(rt);
            });
    }

    public void revokeAllUserTokens(String username) {
        refreshTokenRepo.findByUsername(username).ifPresent(rt -> {
            rt.setRevoked(true);
            rt.setRevokedAt(Instant.now());
        });
        refreshTokenRepo.saveAll(
            refreshTokenRepo.findByUsername(username)
                .map(Collections::singletonList)
                .orElseGet(ArrayList::new));
    }

    public List<SimpleGrantedAuthority> getAuthorities(String username) {
        return userRepo.findByUserName(username)
            .map(u -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANGLER")))
            .orElseGet(ArrayList::new);
    }

    public void initiatePasswordReset(String email) {
        userRepo.findByEmail(email).ifPresent(user -> {
            String resetToken = UUID.randomUUID().toString();
            // Store with expiry (1 hour)
            // Send email with link
        });
    }

    public void resetPassword(String token, String newPassword) {
        // Verify token, update password
        // Invalidate all refresh tokens
    }

    public UserDto getCurrentUser(String email) {
        return userRepo.findByEmail(email)
            .map(this::mapToDto)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private String hashToken(String token) {
        // Use BCrypt or SHA-256
        return passwordEncoder.encode(token);
    }

    private UserDto mapToDto(User user) {
        return new UserDto(
            user.getId(), user.getUserName(), user.getFirstName(),
            user.getLastName(), user.getEmail(), user.getPhoneNumber(),
            user.getCreatedAt()
        );
    }
}