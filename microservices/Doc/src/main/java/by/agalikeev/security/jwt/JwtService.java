package by.agalikeev.security.jwt;

import by.agalikeev.dto.security.JwtAuthenticationDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {

  private static final Logger LOGGER = LogManager.getLogger(JwtService.class);

  @Value("JwtSecret=U2VjdXJlU2VjcmV0SldUMjU2Qml0TGVuZ3RoU2hvdWxkQmVBdExlYXN0MzJDow==")
  private String jwtSecret;

  public JwtAuthenticationDTO generateAuthToken(String email) {
    JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
    jwtDTO.setToken(generateJwtToken(email));
    jwtDTO.setRefreshToken(generateRefreshToken(email));
    return jwtDTO;
  }

  public JwtAuthenticationDTO refreshBaseToken(String email, String refreshToken) {
    JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
    jwtDTO.setToken(generateJwtToken(email));
    jwtDTO.setRefreshToken(refreshToken);
    return jwtDTO;
  }

  public String getEmailFromToken(String token) {
    Claims claims = Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
              .verifyWith(getSignKey())
              .build()
              .parseSignedClaims(token)
              .getPayload();
      return true;
    } catch (ExpiredJwtException expEx) {
      LOGGER.error("Expired JWT token", expEx);
    } catch (UnsupportedJwtException expEx) {
      LOGGER.error("Unsupported JWT token", expEx);
    } catch (MalformedJwtException expEx) {
      LOGGER.error("Malformed JWT token", expEx);
    } catch (SecurityException expEx) {
      LOGGER.error("Security exception", expEx);
    } catch (Exception expEx) {
      LOGGER.error("Invalid token", expEx);
    }
    return false;
  }

  private String generateJwtToken(String email) {
    Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
            .subject(email)
            .expiration(date)
            .signWith(getSignKey())
            .compact();
  }

  private String generateRefreshToken(String email) {
    Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
            .subject(email)
            .expiration(date)
            .signWith(getSignKey(), Jwts.SIG.HS256)
            .compact();
  }

  private SecretKey getSignKey() {
    try {
      if (!jwtSecret.matches("^[A-Za-z0-9+/=]+$")) {
        throw new IllegalArgumentException("Invalid Base64 secret");
      }
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
    } catch (IllegalArgumentException e) {
      LOGGER.error("Invalid JWT secret: {}", e.getMessage());
      throw new JwtException("JWT secret configuration error");
    }
  }
}
