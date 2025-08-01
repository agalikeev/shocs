package by.agalikeev.controller;

import by.agalikeev.dto.request.TodoPointRequest;
import by.agalikeev.entity.TodoPoint;
import by.agalikeev.service.TodoPointService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/todo/point")
public class TodoPointController {
  private final TodoPointService todoPointService;

  @GetMapping
  public List<TodoPoint> getAll() {
    return todoPointService.findAll();
  }

  @GetMapping("/{id}")
  public TodoPoint getById(@PathVariable Long id) {
    return todoPointService.findById(id);
  }

  @PostMapping
  public TodoPoint create(@RequestBody TodoPointRequest todoPointRequest) {
    return todoPointService.save(todoPointRequest);
  }

  @PutMapping
  public TodoPoint update(@RequestBody TodoPoint todoPoint) {
    return todoPointService.update(todoPoint);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    todoPointService.deleteById(id);
  }
}
