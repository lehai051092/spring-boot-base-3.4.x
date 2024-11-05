package example.baseproject.module.auth.controller;

import example.baseproject.module.auth.dto.ForgotPasswordDto;
import example.baseproject.module.auth.dto.LoginUserDto;
import example.baseproject.module.auth.dto.ResetPasswordDto;
import example.baseproject.module.auth.response.LoginResponse;
import example.baseproject.module.auth.service.AuthenticationService;
import example.baseproject.module.auth.service.ForgotPasswordService;
import example.baseproject.module.auth.service.JwtService;
import example.baseproject.module.user.dto.RegisterUserDto;
import example.baseproject.module.user.model.User;
import example.baseproject.module.user.response.UserResponse;
import example.baseproject.module.user.service.UserService;
import example.baseproject.shared.response.ErrorResponse;
import example.baseproject.shared.response.SuccessResponse;
import example.baseproject.shared.response.swagger.SuccessLoginResponse;
import example.baseproject.shared.response.swagger.SuccessUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static example.baseproject.shared.constant.StatusCodeConstant.CREATED;
import static example.baseproject.shared.constant.StatusCodeConstant.SUCCESS;

@RequestMapping("/api/auth")
@RestController
@Tag(name = "Auth Controller", description = "Auth related API")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final ForgotPasswordService forgotPasswordService;

    private final RedisTemplate<String, String> redisTemplate;

    private final UserService userService;

    @Autowired
    public AuthController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            ForgotPasswordService forgotPasswordService,
            RedisTemplate<String, String> redisTemplate, UserService userService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.forgotPasswordService = forgotPasswordService;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @Operation(
            summary = "Create a new user",
            description = "Returns register information of user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessUserResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<UserResponse>> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserResponse registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(new SuccessResponse<>(CREATED, "Registration successfully", registeredUser));
    }

    @Operation(
            summary = "Handle login user",
            description = "Returns information of user when handle login"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessLoginResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(new SuccessResponse<>(SUCCESS, "Login successfully", loginResponse));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<SuccessResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordDto request) {
        forgotPasswordService.sendForgotPasswordEmail(request.getEmail());
        return ResponseEntity.ok(new SuccessResponse<>(SUCCESS, "Forgot password successfully", request.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponse<UserResponse>> resetPassword(@Valid @RequestBody ResetPasswordDto request) throws Exception {
        // Kiểm tra token từ Redis
        String redisKey = request.getEmail() + "_forgot_password_token";
        String storedToken = redisTemplate.opsForValue().get(redisKey);

        if (storedToken == null || !storedToken.equals(request.getToken())) {
            throw new Exception("Invalid or expired token.");
        }

        UserResponse userResponse = this.userService.updatePasswordByEmail(request.getEmail());

        // Sau khi đặt lại mật khẩu thành công, xóa token khỏi Redis
        redisTemplate.delete(redisKey);

        return ResponseEntity.ok(new SuccessResponse<>(SUCCESS, "Reset password successfully", userResponse));
    }
}
