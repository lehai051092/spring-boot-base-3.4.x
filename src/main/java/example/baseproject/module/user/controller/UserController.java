package example.baseproject.module.user.controller;

import example.baseproject.module.user.model.User;
import example.baseproject.module.user.response.UserResponse;
import example.baseproject.module.user.service.UserService;
import example.baseproject.shared.response.ErrorResponse;
import example.baseproject.shared.response.SuccessResponse;
import example.baseproject.shared.response.swagger.SuccessListUserResponse;
import example.baseproject.shared.response.swagger.SuccessUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static example.baseproject.shared.constant.StatusCodeConstant.SUCCESS;

@RequestMapping("/api/users")
@RestController
@Tag(name = "User Controller", description = "User related API")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current user information",
            description = "Returns information of the currently logged in user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
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
    public ResponseEntity<SuccessResponse<UserResponse>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserResponse userResponse = this.userService.convertUserResponse(currentUser);

        return ResponseEntity.ok(new SuccessResponse<>(SUCCESS, "Get user successful", userResponse));
    }

    @GetMapping
    @Operation(
            summary = "Get list user",
            description = "Returns list user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessListUserResponse.class)
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
    public ResponseEntity<SuccessResponse<List<UserResponse>>> allUsers() {
        List<UserResponse> users = userService.allUsers();

        return ResponseEntity.ok(new SuccessResponse<>(SUCCESS, "Get list users successful", users));
    }
}
