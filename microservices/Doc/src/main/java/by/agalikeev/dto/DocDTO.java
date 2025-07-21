package by.agalikeev.dto;

import lombok.Data;

@Data
public class DocDTO {
  private String title;
  private String content;
  private boolean isPublic;
}
