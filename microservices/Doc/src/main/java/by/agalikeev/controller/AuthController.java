package by.agalikeev.controller;

import by.agalikeev.dto.security.JwtAuthenticationDTO;
import by.agalikeev.dto.security.RefreshTokenDTO;
import by.agalikeev.dto.security.UserCredentialDTO;
import by.agalikeev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/signin")
  public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody UserCredentialDTO userCredentialDTO) {
    try {
      JwtAuthenticationDTO jwtAuthenticationDTO = userService.signIn(userCredentialDTO);
      return ResponseEntity.ok(jwtAuthenticationDTO);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @PostMapping("/refresh")
  public JwtAuthenticationDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
    return userService.refreshToken(refreshTokenDTO);
  }
}
