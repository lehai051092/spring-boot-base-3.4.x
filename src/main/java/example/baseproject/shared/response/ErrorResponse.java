package example.baseproject.shared.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int status;

    private String message;

    private Object error;

    public ErrorResponse(int status, String message, Object error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
