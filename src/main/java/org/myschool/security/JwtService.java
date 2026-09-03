package org.myschool.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.myschool.enumeration.Role;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "cette-dure-cle-doit-faire-au-moins-256-bits-pour-etre-valide-avec-hs256".getBytes()
    );

    private final long tokenValidityInMilliSeconds = 1000 * 60 * 60;  // 1h

    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenValidityInMilliSeconds))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
