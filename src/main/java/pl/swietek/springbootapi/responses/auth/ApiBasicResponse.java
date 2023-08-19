package pl.swietek.springbootapi.responses.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiBasicResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;
}
