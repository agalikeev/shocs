package by.agalikeev.service;

import by.agalikeev.dto.request.TodoPointDTO;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.repository.TodoPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoPointService {

  private final TodoPointRepository todoPointRepository;

  private final TodoListService todoListService;

  public List<TodoPoint> findAll() {
    return todoPointRepository.findAll();
  }

  public TodoPoint findById(Long id) {
    return todoPointRepository.findById(id).orElse(null);
  }

  public TodoPoint save(TodoPointDTO todoPointDTO) {
    TodoPoint todoPoint = TodoPoint.builder()
            .title(todoPointDTO.title())
            .description(todoPointDTO.description())
            .todoList(todoListService.findById(todoPointDTO.list_id()))
            .build();
    return todoPointRepository.save(todoPoint);
  }

  public void deleteById(Long id) {
    todoPointRepository.deleteById(id);
  }

  public TodoPoint update(TodoPoint todoPoint) {
    return todoPointRepository.save(todoPoint);
  }
}
