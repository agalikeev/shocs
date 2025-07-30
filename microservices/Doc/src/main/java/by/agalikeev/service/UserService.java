package by.agalikeev.service;

import by.agalikeev.dto.JwtAuthenticationDTO;
import by.agalikeev.dto.RefreshTokenDTO;
import by.agalikeev.dto.UserCredentialDTO;
import by.agalikeev.dto.request.UserDTO;
import by.agalikeev.entity.User;
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

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(UserDTO userDTO) {
//    TODO: перенести в mapper userDTO to User
    User user = User.builder()
            .username(userDTO.username())
            .password(passwordEncoder.encode(userDTO.password()))
            .email(userDTO.email())
            .roles(userDTO.roles())
            .build();
    return userRepository.save(user);
  }

  private User getByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    Optional<User> optionalUser = userRepository.findByEmail(userCredentialDTO.getEmail());
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword())) {
        return user;
      }
    }
    throw new AuthenticationException("Email or password is incorrect");
  }

  public User getByEmail(String email) throws Exception {
    return userRepository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
  }

  public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    User user = getByCredentials(userCredentialDTO);
    return jwtService.generateAuthToken(user.getEmail());
  }

  public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
    String refreshToken = refreshTokenDTO.getRefreshToken();
    if (refreshToken != null && jwtService.validateToken(refreshToken)) {
      User user = getByEmail(jwtService.getEmailFromToken(refreshToken));
      return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
    }
    throw new AuthenticationException("Refresh token is incorrect");
  }
}
