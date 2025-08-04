package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoPointUpdateRequest(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description
) {
}
