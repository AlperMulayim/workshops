package com.alper.jwtsecurity.tokens;

import com.alper.jwtsecurity.users.MyUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class MyJwtService {
    private final String SECRET_KEY = "secretadsadfdsfdsfdsfdsfsdsfsdsdadsdadsaasdas";
    private  String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return  username.equals(userDetails.getUsername());
    }

    public String extractUserName(String token) {
        return  extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token);

        return  claimsResolver.apply(jws.getBody());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims,userDetails.getUsername());
    }
}
