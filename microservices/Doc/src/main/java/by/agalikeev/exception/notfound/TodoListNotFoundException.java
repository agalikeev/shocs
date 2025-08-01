package by.agalikeev.exception.notfound;

public class TodoListNotFoundException extends NotFoundException {
  public TodoListNotFoundException(Long id) {
    super("TodoList with id " + id + " not found");
  }
}
