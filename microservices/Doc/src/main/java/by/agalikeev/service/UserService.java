package by.agalikeev.service;

import by.agalikeev.dto.request.UserDTO;
import by.agalikeev.entity.User;
import by.agalikeev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(UserDTO userDTO) {
    User user = User.builder()
            .username(userDTO.username())
            .password(passwordEncoder.encode(userDTO.password()))
            .email(userDTO.email())
            .roles(userDTO.roles())
            .build();
    return userRepository.save(user);
  }
}
