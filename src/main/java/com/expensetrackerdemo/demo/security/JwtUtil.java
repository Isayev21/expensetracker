package com.expensetrackerdemo.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String secret = "MyUltraSecureJwtSecretKey_2025$$";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}
