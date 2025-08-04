package by.agalikeev.mapper;

import by.agalikeev.dto.request.TodoPointCreateRequest;
import by.agalikeev.dto.request.TodoPointUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TodoPointMapper {
  public TodoPoint createTodoPoint(TodoPointCreateRequest request, User author, TodoList todoList) {
    return TodoPoint.builder()
            .title(request.title())
            .description(request.description())
            .author(author)
            .todoList(todoList)
            .build();
  }

  public TodoPoint updateTodoPoint(TodoPointUpdateRequest request, TodoPoint todoPoint) {
    todoPoint.setTitle(getOrDefault(request.title(), todoPoint.getTitle()));
    todoPoint.setDescription(getOrDefault(request.description(), todoPoint.getDescription()));
    return todoPoint;
  }

  public TodoPoint makePointDone(TodoPoint todoPoint) {
    todoPoint.setDone(true);
    return todoPoint;
  }

  private <T> T getOrDefault(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }
}
