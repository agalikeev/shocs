package by.agalikeev.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoRoles {
  READER("reader"),

  EDITOR("editor"),

  ADMIN("admin");

  private final String role;
}
