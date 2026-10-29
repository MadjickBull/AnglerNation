package com.anglernation.security;

import com.anglernation.models.AbstractEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens", indexes = {
    @Index(name = "idx_refresh_token_hash", columnList = "token_hash"),
    @Index(name = "idx_refresh_token_username", columnList = "username")
})
public class RefreshToken extends AbstractEntity {

    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "replaced_by_token_hash")
    private String replacedByTokenHash; // For rotation tracking

    // Getters and setters

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getReplacedByTokenHash() {
        return replacedByTokenHash;
    }

    public void setReplacedByTokenHash(String replacedByTokenHash) {
        this.replacedByTokenHash = replacedByTokenHash;
    }
}