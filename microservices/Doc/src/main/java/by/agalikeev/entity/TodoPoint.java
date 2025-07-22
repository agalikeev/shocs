package by.agalikeev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoPoint {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @ManyToOne
  @JoinColumn(name = "list_id", nullable = false)
  private TodoList todoList;

  private String title;

  private String description;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "solver_id", nullable = false)
  private User solver;

  private Timestamp createdAt;

  private Timestamp updatedAt;

  private boolean completed;
}
