package ru.innopolis.demo.configurations;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.innopolis.demo.models.UserProfileDetails;

import java.util.Date;

@Log
@Service
public class TokenAuthenticationProvider {
    @Value("${jwt.secret}")
    private String tokenSecret;
    @Value("${jwt.expiration}")
    private Long tokenExpiration;

    public String createToken(Authentication authentication) {
        UserProfileDetails userPrincipal = (UserProfileDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration);

        return Jwts.builder()
                .claim("id", userPrincipal.getId())
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Long getIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.warning("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.warning("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.warning("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.warning("JWT claims string is empty.");
        }
        return false;
    }
}
