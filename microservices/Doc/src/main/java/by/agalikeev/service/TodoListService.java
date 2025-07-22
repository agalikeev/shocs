package by.agalikeev.service;

import by.agalikeev.entity.TodoList;
import by.agalikeev.repository.TodoListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoListService {

  private final TodoListRepository todoListRepository;

  public List<TodoList> findAll() {
    return todoListRepository.findAll();
  }

  public TodoList findById(Long id) {
    return todoListRepository.findById(id).orElse(null);
  }

  public TodoList save(TodoList todoList) {
    return todoListRepository.save(todoList);
  }

  public void deleteById(Long id) {
    todoListRepository.deleteById(id);
  }
}
