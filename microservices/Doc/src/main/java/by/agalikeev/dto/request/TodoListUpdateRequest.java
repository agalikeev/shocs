package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoListUpdateRequest(
        @NotBlank @JsonProperty("id") Long id,
        @JsonProperty("description") String description,
        @JsonProperty("title") String title
) {
}
