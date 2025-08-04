package by.agalikeev.exception.notfound;

public class TodoPointNotFoundException extends NotFoundException {
  public TodoPointNotFoundException(Long listId, Long pointId) {
    super("Todo point with pointId " + pointId + " and listId " + listId + " not found");
  }

  public TodoPointNotFoundException() {
    super("Todo points not found");
  }
}
