package com.lsc.software.api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secretKey;

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
                .signWith(getSecretKey())
                .issuedAt(now)
                .id(UUID.randomUUID().toString())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        byte[] encodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public Authentication getAuthentication(String token, UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
