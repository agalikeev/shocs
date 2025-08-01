package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoPointRequest(
        @NotNull @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("done") String done,
        @NotNull @JsonProperty("list_id") Long list_id
) {
}
