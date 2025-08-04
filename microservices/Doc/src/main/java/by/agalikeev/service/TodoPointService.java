package by.agalikeev.service;

import by.agalikeev.dto.request.TodoPointCreateRequest;
import by.agalikeev.dto.request.TodoPointUpdateRequest;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.exception.notfound.TodoListNotFoundException;
import by.agalikeev.exception.notfound.TodoPointNotFoundException;
import by.agalikeev.mapper.TodoPointMapper;
import by.agalikeev.repository.TodoPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public List<TodoPoint> findAll(Long listId) {
    return Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 3))
            .map(todoPointRepository::findByTodoList)
            .orElseThrow(() -> new TodoListNotFoundException(listId))
            .orElseThrow(TodoPointNotFoundException::new);
  }

  @Transactional(readOnly = true)
  public TodoPoint findById(Long listId, Long pointId) {
    return Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 3))
            .map(foundList -> todoPointRepository.findByTodoListAndId(foundList, pointId)
                    .orElseThrow(() -> new TodoPointNotFoundException(listId, pointId)))
            .orElseThrow(() -> new TodoListNotFoundException(listId));
  }

  @Transactional
  public TodoPoint save(Long listId, TodoPointCreateRequest request) {
    return Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 2))
            .map(foundList -> todoPointRepository.save(
                    todoPointMapper.createTodoPoint(request, userService.getUser(), foundList))
            )
            .orElseThrow(() -> new TodoListNotFoundException(listId));
  }

  @Transactional
  public void deleteById(Long listId, Long pointId) {
    Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 2))
            .map(foundList -> {
                      if (todoPointRepository.deleteByTodoListAndId(foundList, pointId) == 0) {
                        throw new TodoPointNotFoundException(listId, pointId);
                      }
                      return true;
                    }
            )
            .orElseThrow(() -> new TodoListNotFoundException(listId));
  }

  @Transactional
  public TodoPoint update(Long listId, Long pointId, TodoPointUpdateRequest request) {
    return Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 2))
            .map(foundList -> todoPointRepository.findByTodoListAndId(foundList, pointId)
                    .orElseThrow(() -> new TodoPointNotFoundException(listId, pointId)))
            .map(foundPoint -> todoPointRepository.save(todoPointMapper.updateTodoPoint(request, foundPoint)))
            .orElseThrow(() -> new TodoListNotFoundException(listId));
  }

  @Transactional
  public void markDone(Long listId, Long pointId) {
    Optional.ofNullable(todoListService.findById(listId))
            .filter(filterList -> permissionService.hasPermission(filterList, 2))
            .map(foundList -> todoPointRepository.findByTodoListAndId(foundList, pointId)
                    .orElseThrow(() -> new TodoPointNotFoundException(listId, pointId)))
            .map(foundPoint -> todoPointRepository.save(todoPointMapper.makePointDone(foundPoint)))
            .orElseThrow(() -> new TodoListNotFoundException(listId));
  }
}
