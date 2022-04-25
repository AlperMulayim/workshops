package com.alper.jwtsecurity.tokens;

import com.alper.jwtsecurity.users.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class MyJwtService {
    private final String SECRET_KEY = "secret";
    private  String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Keys.secretKeyFor(SignatureAlgorithm.HS256)).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return  username.equals(userDetails.getUsername());
    }

    private String extractUserName(String token) {
        return  extractClaim(token,Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
        return  claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims,userDetails.getUsername());
    }
}
