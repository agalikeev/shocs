package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoListUpdateRequest(
        @JsonProperty("id") Long id,
        @JsonProperty("description") String description,
        @JsonProperty("title") String title
) {
}
