package by.agalikeev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"list_id", "user_id"})
})
public class Permission extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "list_id", nullable = false)
  private TodoList todoList;

  @Column(name = "role", nullable = false)
  private Integer permissionLevel;  // 1 - admin, 2 - editor, 3 - reader, 4 - undefined

  @PrePersist
  @Override
  public void onCreate() {
    super.onCreate();
  }

  @PreUpdate
  @Override
  public void onUpdate() {
    super.onUpdate();
  }
}
