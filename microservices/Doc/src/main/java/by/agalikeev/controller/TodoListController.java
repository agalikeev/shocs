package by.agalikeev.controller;

import by.agalikeev.dto.request.TodoListCreateRequest;
import by.agalikeev.dto.request.TodoListUpdateRequest;
import by.agalikeev.entity.TodoList;
import by.agalikeev.service.TodoListService;
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
@RequestMapping("/todo/list")
public class TodoListController {

  private final TodoListService todoListService;

  @GetMapping
  public ResponseEntity<List<TodoList>> readAll() {
    return ResponseEntity.ok(todoListService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoList> read(@PathVariable Long id) {
    return ResponseEntity.ok(todoListService.findById(id));
  }

  @PostMapping
  public ResponseEntity<TodoList> create(@RequestBody @Valid TodoListCreateRequest request) {
    return ResponseEntity.ok(todoListService.create(request));
  }

  @PutMapping
  public ResponseEntity<TodoList> update(@RequestBody @Valid TodoListUpdateRequest request) {
    return ResponseEntity.ok(todoListService.update(request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    return ResponseEntity.ok(todoListService.deleteById(id));
  }
}
