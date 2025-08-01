package by.agalikeev.service;

import by.agalikeev.entity.Permission;
import by.agalikeev.entity.TodoList;
import by.agalikeev.entity.User;
import by.agalikeev.mapper.PermissionMapper;
import by.agalikeev.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

  private final PermissionRepository permissionRepository;

  private final UserService userService;

  private final PermissionMapper permissionMapper;

  public void addPermission(TodoList todoList, User user, Integer permissionLevel) {
    Permission permission = permissionMapper.toPermission(todoList, user, permissionLevel);
    permissionRepository.save(permission);
  }

  public Boolean hasPermission(TodoList todoList, Integer enoughPermissionLevel) {
    return getPermissionLevel(todoList) <= enoughPermissionLevel;
  }

  private Integer getPermissionLevel(TodoList todoList) {
    return permissionRepository.findByTodoListAndUser(todoList, userService.getUser())
            .map(Permission::getPermissionLevel)
            .orElse(4);
  }
}
