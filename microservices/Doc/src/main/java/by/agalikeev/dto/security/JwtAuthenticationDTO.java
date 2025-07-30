package by.agalikeev.dto.security;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
  private String token;
  private String refreshToken;
}
