package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoPointDTO(
        @JsonProperty("description") String description,
        @JsonProperty("title") String title,
        @JsonProperty("done") String done,
        @JsonProperty("list_id") Long list_id
) {
}
