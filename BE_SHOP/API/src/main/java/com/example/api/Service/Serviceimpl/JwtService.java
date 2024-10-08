package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtService {
private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpiration= 60*60*24*30;//time het han token

 //tao token

public  String generationtoken(User user){
    return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime()+ jwtExpiration *1000L))
            .signWith(secretKey)
            .compact();
}
//trich USername from token

public String extractUsernamefromToken(String token){
  return   Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();


}

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()

                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }
    public long getExpirationTime(){
        return jwtExpiration;
    }



    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
