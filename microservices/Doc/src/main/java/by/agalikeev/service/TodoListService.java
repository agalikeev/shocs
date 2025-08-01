package by.agalikeev.service;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.dto.request.TodoListUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.exception.notfound.TodoListNotFoundException;
import by.agalikeev.mapper.TodoListMapper;
import by.agalikeev.repository.TodoListRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoListService {

  private final TodoListRepository todoListRepository;

  private final ModelMapper modelMapper;

  private final TodoListMapper todoListMapper;

  private final PermissionService permissionService;

  private final UserService userService;

  public List<TodoList> findAll() {
    return todoListRepository.findAll();
  }

  public TodoList findById(Long id) {
    return todoListRepository.findById(id)
            .filter(todoList -> permissionService.hasPermission(todoList, 3))
            .orElseThrow(() -> new TodoListNotFoundException(id));
  }

  public TodoList create(TodoListCreateRequest request) {
    TodoList todoList = todoListMapper.toTodoList(request);
    permissionService.addPermission(todoList, userService.getUser(), 1);
    return todoListRepository.save(todoList);
  }

  public TodoList update(TodoListUpdateRequest request) {
    return todoListRepository.findById(request.id())
            .filter(todoList -> permissionService.hasPermission(todoList, 2))
            .map(todoList -> {
              modelMapper.map(request, todoList);
              return todoListRepository.save(todoList);
            })
            .orElseThrow(() -> new TodoListNotFoundException(request.id()));
  }

  public String deleteById(Long id) {
    return todoListRepository.findById(id)
            .filter(todoList -> permissionService.hasPermission(todoList, 1))
            .map(todoList -> {
              todoListRepository.delete(todoList);
              return "Deleted TodoList with id " + todoList.getId();
            })
            .orElseThrow(() -> new SecurityException("Not enough permissions to deleting"));
  }
}
