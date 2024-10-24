package example.baseproject.module.user.controller;

import example.baseproject.module.user.model.User;
import example.baseproject.module.user.service.UserService;
import example.baseproject.shared.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static example.baseproject.shared.constant.StatusCodeConstant.SUCCESS;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new SuccessResponse(SUCCESS, "Get user successful", currentUser));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(new SuccessResponse(SUCCESS, "Get list users successful", users));
    }
}
