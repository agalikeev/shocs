package by.agalikeev.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequest(
        @NotBlank @JsonProperty("username") String username,
        @NotBlank @JsonProperty("password") String password,
        @NotBlank @JsonProperty("email") String email,
        @JsonProperty("roles") String roles
) {
}
