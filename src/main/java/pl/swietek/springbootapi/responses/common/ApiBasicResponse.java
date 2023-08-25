package pl.swietek.springbootapi.responses.common;

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
