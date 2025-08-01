package by.agalikeev.controller;

import by.agalikeev.dto.request.UserRequest;
import by.agalikeev.dto.response.UserDto;
import by.agalikeev.entity.User;
import by.agalikeev.exception.notfound.UserNotFoundException;
import by.agalikeev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
    return ResponseEntity.ok(userService.createUser(userRequest));
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<User> getByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userService.getByEmail(email));
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> getMe() {
    return ResponseEntity.ok(userService.getUser());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<UserNotFoundException> handleEntityNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new UserNotFoundException(ex.getMessage()));
  }
}
