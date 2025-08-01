package by.agalikeev.mapper;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.dto.request.TodoListUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TodoListMapper {

  private final UserService userService;

  public TodoList createTodoList(TodoListCreateRequest request) {
    return TodoList.builder()
            .title(request.title())
            .description(request.description())
            .author(userService.getUser())
            .build();
  }

  public TodoList updateTodoList(TodoListUpdateRequest request, TodoList todoList) {
    todoList.setTitle(getOrDefault(request.title(), todoList.getTitle()));
    todoList.setDescription(getOrDefault(request.description(), todoList.getDescription()));
    return todoList;
  }

  private <T> T getOrDefault(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }
}
