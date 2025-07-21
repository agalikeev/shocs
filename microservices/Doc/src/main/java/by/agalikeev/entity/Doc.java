package by.agalikeev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doc {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  private String title;
  private String content;
  private long ownerId;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private boolean isPublic;
}
