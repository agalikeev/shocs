package by.agalikeev.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity implements Auditable{

  @Column(name = "created", nullable = false, updatable = false)
  private Instant created;

  @Column(name = "updated", nullable = false)
  private Instant updated;
}
