package by.agalikeev.mapper;

import by.agalikeev.entity.Permission;
import by.agalikeev.entity.TodoList;
import by.agalikeev.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
  public Permission toPermission(TodoList todoList, User user, Integer permissionLevel) {
    return Permission.builder()
            .permissionLevel(permissionLevel)
            .user(user)
            .todoList(todoList)
            .user(user)
            .build();
  }
}
