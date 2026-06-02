package com.simplyfly.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility{

    private static final String SECRET_KEY= "c2ltcGx5Zmx5c2VjcmV0a2V5Zm9yandiYXNlZDY0ZW5jb2RlZHN0cmluZw==";

    public String generateToken(String email,String role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role);
        return createToken(claims,email);
    }

    private String createToken(Map<String,Object> claims,String email){
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L*60*60*24))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token,String username){
        String extractedUsername=extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String extractRole(String token){
        return extractClaim(token,claims -> claims.get("role",String.class));
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token,Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}