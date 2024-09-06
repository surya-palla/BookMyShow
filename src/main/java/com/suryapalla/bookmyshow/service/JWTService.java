package com.suryapalla.bookmyshow.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretKey = "";

    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGen.generateKey();
            byte[] keyBytes = key.getEncoded();
            secretKey = Base64.getEncoder().encodeToString(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token =  Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(60*60*30)))
                .and()
                .signWith(getKey())
                .compact();
        return token;
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String bearerToken) {
        return extractClaim(bearerToken, Claims::getSubject);
    }

    private <T> T extractClaim(String bearerToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(bearerToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String bearerToken) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(bearerToken).getPayload();
    }

    public boolean validateToken(String bearerToken, UserDetails userDetails) {
        final String username = extractUserName(bearerToken);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(bearerToken));
    }

    private boolean isTokenExpired(String bearerToken) {
        return extractExpiration(bearerToken).before(new Date());
    }

    private Date extractExpiration(String bearerToken) {
        return extractClaim(bearerToken, Claims::getExpiration);
    }
}
