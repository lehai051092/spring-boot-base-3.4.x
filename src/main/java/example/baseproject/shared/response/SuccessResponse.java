package example.baseproject.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> {

    @Schema(example = "200", description = "Status of response")
    private int status;

    @Schema(example = "Get user successful", description = "Message of feedback")
    private String message;

    @Schema(description = "Feedback data")
    private T data;

    public SuccessResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
