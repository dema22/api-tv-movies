package com.example.spring.security;

import com.example.spring.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {
    private final String jwtSecret  = "ut1FfO9sSPjG1OKxVh";

    public String generateToken(User user) {
        //System.out.println(new Date(System.currentTimeMillis() +  120000));
        System.out.println("Exp in Seconds: " + (System.currentTimeMillis() +  120000) / 1000);
        System.out.println("Exp in Milliseconds : " + (System.currentTimeMillis() +  120000));

        return Jwts.builder()
                .setSubject(format("%s,%s", user.getIdUser(), user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +  120000))// it will expire after 2 minutes.
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Integer getUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        String userId = claims.getSubject().split(",")[0];
        return Integer.parseInt(userId);
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            System.out.println("Invalid token");
            return false;
        }
    }

    public String getTokenFromAuthorizationHeader(String header){
        return header.split(" ")[1].trim();
    }
}
