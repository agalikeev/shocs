package by.agalikeev.service;

import by.agalikeev.dto.request.UserRequest;
import by.agalikeev.dto.response.UserDto;
import by.agalikeev.dto.security.JwtAuthenticationDTO;
import by.agalikeev.dto.security.RefreshTokenDTO;
import by.agalikeev.dto.security.UserCredentialDTO;
import by.agalikeev.entity.User;
import by.agalikeev.mapper.UserMapper;
import by.agalikeev.repository.UserRepository;
import by.agalikeev.security.CustomUserDetails;
import by.agalikeev.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public User createUser(UserRequest userRequest) {
    return userRepository.save(userMapper.toUser(userRequest));
  }

  @Transactional
  public UserDto getUserDto() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
    User user = details.getUser();
    return userMapper.toUserDto(getByEmail(user.getEmail()));
  }

  private User getByCredentials(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    return userRepository.findByEmail(userCredentialDTO.getEmail())
            .filter(user -> passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword()))
            .orElseThrow(() -> new AuthenticationException("Email or password is incorrect"));
  }

  @Transactional(readOnly = true)
  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  public JwtAuthenticationDTO signIn(UserCredentialDTO userCredentialDTO) throws AuthenticationException {
    User user = getByCredentials(userCredentialDTO);
    return jwtService.generateAuthToken(user.getEmail());
  }

  @Transactional
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

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
    return details.getUser();
  }
}
