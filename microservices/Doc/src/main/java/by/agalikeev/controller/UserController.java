package by.agalikeev.controller;

import by.agalikeev.dto.UserDTO;
import by.agalikeev.entity.User;
import by.agalikeev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/new")
  public User createUser(@RequestBody UserDTO userDTO) {
    return userService.createUser(userDTO);
  }

  @GetMapping("/all")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/welcome")
  public String welcome() {
    return "Hello World! It`s welcome page ";
  }
}
