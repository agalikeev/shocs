package by.agalikeev.controller;

import by.agalikeev.dto.DocDTO;
import by.agalikeev.entity.Doc;
import by.agalikeev.service.DocService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DocController {
  private final DocService docService;

  @GetMapping
  public ResponseEntity<List<Doc>> getAll() {
    return ResponseEntity.ok(docService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Doc> get(@PathVariable Long id) {
    return ResponseEntity.ok(docService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Doc> create(@RequestBody DocDTO docDTO) {
    return ResponseEntity.ok(docService.create(docDTO));
  }

  @PutMapping
  public ResponseEntity<Doc> update(@RequestBody Doc doc) {
    return ResponseEntity.ok(docService.update(doc));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    docService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
