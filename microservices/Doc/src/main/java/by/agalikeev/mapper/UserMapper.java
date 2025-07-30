package by.agalikeev.mapper;


import by.agalikeev.dto.request.UserDTO;
import by.agalikeev.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User toUser(UserDTO userDTO) {
    return User.builder()
            .username(userDTO.username())
            .password(passwordEncoder.encode(userDTO.password()))
            .email(userDTO.email())
            .roles(userDTO.roles())
            .build();
  }
}
