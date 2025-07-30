package by.agalikeev.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
  private String token;
  private String refreshToken;
}
