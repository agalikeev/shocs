package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoPointCreateRequest(
        @NotNull @JsonProperty("title") String title,
        @JsonProperty("description") String description
) {
}
