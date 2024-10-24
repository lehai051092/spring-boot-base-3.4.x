package example.baseproject.shared.response.swagger;

import example.baseproject.module.user.response.UserResponse;
import example.baseproject.shared.response.SuccessResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Successful response to user information")
public class SuccessUserResponse extends SuccessResponse<UserResponse> {

    public SuccessUserResponse(int status, String message, UserResponse data) {
        super(status, message, data);
    }
}
