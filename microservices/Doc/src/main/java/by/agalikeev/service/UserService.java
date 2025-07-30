package by.agalikeev.service;

import by.agalikeev.dto.request.UserDTO;
import by.agalikeev.dto.security.JwtAuthenticationDTO;
import by.agalikeev.dto.security.RefreshTokenDTO;
import by.agalikeev.dto.security.UserCredentialDTO;
import by.agalikeev.entity.User;
import by.agalikeev.mapper.UserMapper;
import by.agalikeev.repository.UserRepository;
import by.agalikeev.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final UserMapper userMapper;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(UserDTO userDTO) {
    return userRepository.save(userMapper.toUser(userDTO));
  }

  private User getByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    return userRepository.findByEmail(userCredentialDTO.getEmail())
            .filter(user -> passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword()))
            .orElseThrow(() -> new AuthenticationException("Email or password is incorrect"));
  }

  public User getByEmail(String email) throws Exception {
    return userRepository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
  }

  public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    User user = getByCredentials(userCredentialDTO);
    return jwtService.generateAuthToken(user.getEmail());
  }

  public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
    return Optional.ofNullable(refreshTokenDTO.getRefreshToken())
            .filter(jwtService::validateToken)
            .flatMap(refreshToken -> {
              try {
                User user = getByEmail(jwtService.getEmailFromToken(refreshToken));
                return Optional.of(jwtService.refreshBaseToken(user.getEmail(), refreshToken));
              } catch (Exception e) {
                throw new RuntimeException("User not found");
              }
            })
            .orElseThrow(() -> new AuthenticationException("Refresh token is incorrect"));
  }
}
