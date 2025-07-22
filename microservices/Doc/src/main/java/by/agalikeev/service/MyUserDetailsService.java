package by.agalikeev.service;

import by.agalikeev.config.MyUserDetails;
import by.agalikeev.entity.User;
import by.agalikeev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(MyUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
  }
}
