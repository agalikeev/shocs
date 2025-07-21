package by.agalikeev.service;

import by.agalikeev.dto.DocDTO;
import by.agalikeev.entity.Doc;
import by.agalikeev.repository.DocRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocService {

  private final DocRepository docRepository;

  public List<Doc> findAll() {
    return docRepository.findAll();
  }

  public Doc findById(Long id) {
    return docRepository.findById(id).orElse(null);
  }

  public void deleteById(Long id) {
    docRepository.deleteById(id);
  }

  public Doc update(Doc doc) {
    return docRepository.save(doc);
  }

  public Doc create(DocDTO docDTO) {
    Doc doc = Doc.builder()
            .title(docDTO.getTitle())
            .content(docDTO.getContent())
            .isPublic(docDTO.isPublic())
            .build();
    return docRepository.save(doc);
  }
}
