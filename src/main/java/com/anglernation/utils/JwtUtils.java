package com.anglernation.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${app.jwt.access-token-validity-minutes:15}")
    private long accessTokenValidityMinutes;

    @Value("${app.jwt.refresh-token-validity-days:30}")
    private long refreshTokenValidityDays;

    @Value("${app.jwt.issuer:anglernation}")
    private String issuer;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
            this.publicKey = (RSAPublicKey) keyPair.getPublic();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate RSA keys", e);
        }
    }

    public String generateAccessToken(String username, java.util.Collection<?> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidityMinutes * 60 * 1000);

        return Jwts.builder()
            .setSubject(username)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .claim("roles", roles)
            .claim("tokenType", "access")
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenValidityDays * 24 * 60 * 60 * 1000);
        String tokenId = UUID.randomUUID().toString();

        return Jwts.builder()
            .setSubject(username)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .setId(tokenId)
            .claim("tokenType", "refresh")
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public String getTokenType(String token) {
        return Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("tokenType", String.class);
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }
}