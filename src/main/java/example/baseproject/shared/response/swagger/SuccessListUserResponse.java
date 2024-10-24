package example.baseproject.shared.response.swagger;

import example.baseproject.module.user.response.UserResponse;
import example.baseproject.shared.response.SuccessResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Successful response to user information")
public class SuccessListUserResponse extends SuccessResponse<List<UserResponse>> {

    public SuccessListUserResponse(int status, String message, List<UserResponse> data) {
        super(status, message, data);
    }
}
