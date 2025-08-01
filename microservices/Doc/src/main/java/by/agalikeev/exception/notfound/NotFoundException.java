package by.agalikeev.exception.notfound;

import by.agalikeev.exception.ShocsRuntimeException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ShocsRuntimeException {
  protected NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
