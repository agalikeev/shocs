package by.agalikeev.mapper;


import by.agalikeev.dto.request.UserRequest;
import by.agalikeev.dto.response.UserDto;
import by.agalikeev.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User toUser(UserRequest userRequest) {
    return User.builder()
            .username(userRequest.username())
            .password(passwordEncoder.encode(userRequest.password()))
            .email(userRequest.email())
            .roles(userRequest.roles())
            .build();
  }

  public UserDto toUserDto(User User) {
    return UserDto.builder()
            .id(User.getId())
            .username(User.getUsername())
            .email(User.getEmail())
            .roles(User.getRoles())
            .build();
  }
}
