package by.agalikeev.exception.notfound;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String mail) {
    super("User with mail" + mail + " not found");
  }
}
