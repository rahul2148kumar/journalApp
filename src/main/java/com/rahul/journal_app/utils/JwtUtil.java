package com.rahul.journal_app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Component
public class JwtUtil {

    private String SECRET_KEY="Tak+Hav^uvCHEFsEVfypW#7g9^k*Z8$V";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // you can send your payload here, like username, email, role etc
        return createJwtToken(claims, username);
    }

    private String createJwtToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 minutes
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String jwtToken) {
         Claims allClaims =extractAllClaims(jwtToken);
         return allClaims.getSubject();
    }


    // Get payloads
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean validateToken(String jwtToken) {
        return (!isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        Date expiredDate = extractExpirationDate(jwtToken);
        return expiredDate.before(new Date()); // check expired time(set in jwt token) with current date time
    }

    private Date extractExpirationDate(String jwtToken) {
        return extractAllClaims(jwtToken).getExpiration();
    }
}
