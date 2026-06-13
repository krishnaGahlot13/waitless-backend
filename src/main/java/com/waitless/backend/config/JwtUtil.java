package com.waitless.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // fetching of secret key mentioned in application properties
    private String secret;

    // fetching of expiration duration from application properties
    @Value("${jwt.expiration}")
    private long expiration;

    @PostConstruct // automatically uses this method once the spring created bean of this and dependencies are injected

    // checking of secret key is weak or not
    public void validateSecret() {

        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException(
                    "JWT secret is too weak"
            );
        }
    }

    // get key is used for generating key making the secret into key
    private Key getKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    // generating token while receiving username and role and all stats of token
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }
    // extracting username from subject of token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    // extracting expiration duration from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    public Boolean validateToken(String token,
                                 String username) {

        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }
}