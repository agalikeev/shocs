package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoListCreateRequest(
        @JsonProperty("description") String description,
        @JsonProperty("title") String title
) {
}
