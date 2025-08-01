package by.agalikeev.service;

import by.agalikeev.dto.request.TodoPointRequest;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.exception.notfound.TodoListNotFoundException;
import by.agalikeev.mapper.TodoPointMapper;
import by.agalikeev.repository.TodoPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoPointService {

  private final TodoPointRepository todoPointRepository;

  private final TodoListService todoListService;

  private final TodoPointMapper todoPointMapper;

  private final UserService userService;

  private final PermissionService permissionService;

  public List<TodoPoint> findAll() {
    return todoPointRepository.findAll();
  }

  public TodoPoint findById(Long id) {
    return todoPointRepository.findById(id).orElse(null);
  }

  public TodoPoint save(TodoPointRequest request) {
    return Optional.ofNullable(request)
            .filter(ignored -> permissionService.hasPermission(
                    todoListService.findById(request.list_id()),
                    2
            ))
            .map(req -> todoPointRepository.save(
                            todoPointMapper.createTodoPoint(req, userService.getUser(),
                                    todoListService.findById(req.list_id()))
                    )
            )
            .orElseThrow(() -> {
              assert request != null;
              return new TodoListNotFoundException(request.list_id());
            });
  }

  public void deleteById(Long id) {
    todoPointRepository.deleteById(id);
  }

  public TodoPoint update(TodoPoint todoPoint) {
    return todoPointRepository.save(todoPoint);
  }
}
