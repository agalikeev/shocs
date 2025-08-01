package by.agalikeev.mapper;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.entity.TodoList;
import org.springframework.stereotype.Component;

@Component
public class TodoListMapper {
  public TodoList toTodoList(TodoListCreateRequest request) {
    return TodoList.builder()
            .title(request.title())
            .description(request.description())
            .build();
  }
}
