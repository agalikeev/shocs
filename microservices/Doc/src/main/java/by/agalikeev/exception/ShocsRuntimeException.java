package by.agalikeev.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ShocsRuntimeException extends RuntimeException {

  private final HttpStatus statusCode;

  protected ShocsRuntimeException(String message, HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  protected ShocsRuntimeException(HttpStatus statusCode) {
    super();
    this.statusCode = statusCode;
  }
}
