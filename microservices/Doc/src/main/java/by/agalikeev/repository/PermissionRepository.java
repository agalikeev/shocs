package by.agalikeev.repository;

import by.agalikeev.entity.Permission;
import by.agalikeev.entity.TodoList;
import by.agalikeev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

  Optional<Permission> findByTodoListAndUser(TodoList tl, User user);

  void deleteAllByTodoList(TodoList tl);
}
