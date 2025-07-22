package by.agalikeev.controller;

import by.agalikeev.entity.TodoList;
import by.agalikeev.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoListController {

  private final TodoListService todoListService;

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<TodoList>> readAll() {
    return ResponseEntity.ok(todoListService.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<TodoList> read(@PathVariable long id) {
    return ResponseEntity.ok(todoListService.findById(id));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<TodoList> create(@RequestBody TodoList todoList) {
    return ResponseEntity.ok(todoListService.save(todoList));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    todoListService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
