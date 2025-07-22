package by.agalikeev.service;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.dto.request.TodoListUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.repository.TodoListRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoListService {

  private final TodoListRepository todoListRepository;

  private final ModelMapper modelMapper;

  public List<TodoList> findAll() {
    return todoListRepository.findAll();
  }

  public TodoList findById(Long id) {
    return todoListRepository.findById(id).orElse(null);
  }

  public TodoList save(TodoListCreateRequest request) {
    TodoList todoList = TodoList.builder()
            .title(request.title())
            .description(request.description())
            .build();
    return todoListRepository.save(todoList);
  }

  public TodoList update(TodoListUpdateRequest request) {
    return todoListRepository.findById(request.id())
            .map(todoList -> {
              modelMapper.map(request, todoList);
              return todoListRepository.save(todoList);
            })
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "TodoList with id " + request.id() + " not found"));
  }

  public void deleteById(Long id) {
    todoListRepository.deleteById(id);
  }
}
