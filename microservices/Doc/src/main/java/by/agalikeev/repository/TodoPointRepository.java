package by.agalikeev.repository;

import by.agalikeev.entity.TodoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoPointRepository extends JpaRepository<TodoPoint, Long> {
}
