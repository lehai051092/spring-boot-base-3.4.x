package example.baseproject.shared.response.swagger;

import example.baseproject.module.auth.response.LoginResponse;
import example.baseproject.shared.response.SuccessResponse;

public class SuccessLoginResponse extends SuccessResponse<LoginResponse> {

    public SuccessLoginResponse(int status, String message, LoginResponse data) {
        super(status, message, data);
    }
}
