package by.agalikeev.service;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.dto.request.TodoListUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.exception.notfound.TodoListNotFoundException;
import by.agalikeev.mapper.TodoListMapper;
import by.agalikeev.repository.PermissionRepository;
import by.agalikeev.repository.TodoListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoListService {

  private final TodoListRepository todoListRepository;

  private final TodoListMapper todoListMapper;

  private final PermissionService permissionService;

  private final PermissionRepository permissionRepository;

  private final UserService userService;

  @Transactional(readOnly = true)
  public List<TodoList> findAll() {
    return todoListRepository.findAll();
  }

  @Transactional(readOnly = true)
  public TodoList findById(Long id) {
    return todoListRepository.findById(id)
            .filter(todoList -> permissionService.hasPermission(todoList, 3))
            .orElseThrow(() -> new TodoListNotFoundException(id));
  }
  @Transactional
  public TodoList create(TodoListCreateRequest request) {
    TodoList todoList = todoListMapper.createTodoList(request);
    todoListRepository.save(todoList);
    permissionService.addPermission(todoList, userService.getUser(), 1);
    return todoList;
  }

  @Transactional
  public TodoList update(TodoListUpdateRequest request) {
    return todoListRepository.findById(request.id())
            .filter(todoList -> permissionService.hasPermission(todoList, 2))
            .map(todoList -> todoListRepository.save(todoListMapper.updateTodoList(request, todoList)))
            .orElseThrow(() -> new TodoListNotFoundException(request.id()));
  }

  @Transactional
  public String deleteById(Long id) {
    return todoListRepository.findById(id)
            .filter(todoList -> permissionService.hasPermission(todoList, 1))
            .map(todoList -> {
              permissionRepository.deleteAllByTodoList(todoList);
              todoListRepository.delete(todoList);
              return "Deleted TodoList with id " + todoList.getId();
            })
            .orElseThrow(() -> new SecurityException("Not enough permissions to deleting"));
  }
}
