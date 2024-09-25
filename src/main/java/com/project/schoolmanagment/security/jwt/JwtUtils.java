package com.project.schoolmanagment.security.jwt;


import com.project.schoolmanagment.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  
  private static final Logger LOGGER =  LoggerFactory.getLogger(JwtUtils.class);
  
  @Value("${backendapi.app.jwtExpirationMs}")
  private long jwtExpirations;
  
  @Value("${backendapi.app.jwtSecret}")
  private String jwtSecret;
  
  
  public String generateToken(Authentication authentication) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return buildTokenFromUsername(userDetails.getUsername());
  }

  /**
   * Returns JWT from username that contains issued at and expiration date
   * @param username of logged in user
   * @return token from username
   */
  private String buildTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpirations))
        .signWith(SignatureAlgorithm.HS512,jwtSecret)
        .compact();
  }
  
  public boolean validateToken(String jwtToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
      return true;
    } catch (SignatureException e) {
      LOGGER.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      LOGGER.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      LOGGER.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
  
  public String getUsernameFromToken(String jwtToken) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(jwtToken)
        .getBody()
        .getSubject();
  }
  
  

}
