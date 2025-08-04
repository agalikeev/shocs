package by.agalikeev.controller;

import by.agalikeev.dto.request.TodoPointCreateRequest;
import by.agalikeev.dto.request.TodoPointUpdateRequest;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.service.TodoPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/list/{listId}/point")
public class TodoPointController {

  private final TodoPointService todoPointService;

  @GetMapping
  public ResponseEntity<List<TodoPoint>> getAll(@PathVariable Long listId) {
    return ResponseEntity.ok(todoPointService.findAll(listId));
  }

  @GetMapping("/{pointId}")
  public ResponseEntity<TodoPoint> getById(@PathVariable Long listId, @PathVariable Long pointId) {
    return ResponseEntity.ok(todoPointService.findById(listId, pointId));
  }

  @PostMapping
  public ResponseEntity<TodoPoint> create(@PathVariable @Valid Long listId, @RequestBody @Valid TodoPointCreateRequest todoPointRequest) {
    return ResponseEntity.ok(todoPointService.save(listId, todoPointRequest));
  }

  @PutMapping("/{pointId}")
  public ResponseEntity<TodoPoint> update(
          @PathVariable Long listId,
          @PathVariable Long pointId,
          @RequestBody @Valid TodoPointUpdateRequest request
  ) {
    return ResponseEntity.ok(todoPointService.update(listId, pointId, request));
  }

  @DeleteMapping("/{pointId}")
  public ResponseEntity<Void> delete(@PathVariable Long listId, @PathVariable Long pointId) {
    todoPointService.deleteById(listId, pointId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{pointId}/done")
  public ResponseEntity<Void> markDone(@PathVariable Long listId, @PathVariable Long pointId) {
    todoPointService.markDone(listId, pointId);
    return ResponseEntity.noContent().build();
  }
}
