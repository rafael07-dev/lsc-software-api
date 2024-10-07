package com.lsc.software.api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String createToken(String subject, String roles) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("sub", subject);
        claimsMap.put("roles", roles);

        Date now = new Date();
        long validityInSeconds = 3600000L;

        Date expiration = new Date(now.getTime() + validityInSeconds);

        return Jwts.builder()
                .claims(claimsMap)
                .expiration(expiration)
                .signWith(secretKey)
                .issuedAt(now)
                .id(UUID.randomUUID().toString())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token, UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
