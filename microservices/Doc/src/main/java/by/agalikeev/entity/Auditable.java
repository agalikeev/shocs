package by.agalikeev.entity;

import java.time.Instant;

public interface Auditable {

  void setCreated(Instant created);

  void setUpdated(Instant updated);

  default void onCreate() {
    Instant now = Instant.now();
    setCreated(now);
    setUpdated(now);
  }

  default void onUpdate() {
    setUpdated(Instant.now());
  }
}
