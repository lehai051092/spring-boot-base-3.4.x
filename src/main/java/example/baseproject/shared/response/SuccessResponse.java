package example.baseproject.shared.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {

    private int status;

    private String message;

    private Object data;

    public SuccessResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
