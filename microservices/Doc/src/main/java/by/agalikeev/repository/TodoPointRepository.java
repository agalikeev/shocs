package by.agalikeev.repository;

import by.agalikeev.entity.TodoList;
import by.agalikeev.entity.TodoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoPointRepository extends JpaRepository<TodoPoint, Long> {
  Optional<List<TodoPoint>> findByTodoList(TodoList todoList);

  Integer deleteByTodoListAndId(TodoList todoList, Long id);

  Optional<TodoPoint> findByTodoListAndId(TodoList todoList, Long id);
}
