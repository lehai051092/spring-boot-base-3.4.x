package example.baseproject.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Error response 400, 401, 403, 404, 500")
public class ErrorResponse {

    @Schema(example = "400", description = "Status of response")
    private int status;

    @Schema(example = "An error occurred during authentication", description = "Message of feedback")
    private String message;

    @Schema(description = "Feedback error data")
    private Object error;

    public ErrorResponse(int status, String message, Object error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
