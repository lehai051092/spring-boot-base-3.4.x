package example.baseproject.module.auth.controller;

import example.baseproject.module.auth.dto.LoginUserDto;
import example.baseproject.module.auth.response.LoginResponse;
import example.baseproject.module.auth.service.AuthenticationService;
import example.baseproject.module.auth.service.JwtService;
import example.baseproject.module.user.dto.RegisterUserDto;
import example.baseproject.module.user.model.User;
import example.baseproject.shared.response.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static example.baseproject.shared.constant.StatusCodeConstant.SUCCESS;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(new SuccessResponse(SUCCESS, "Registration successful", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(new SuccessResponse(SUCCESS, "Login successful", loginResponse));
    }
}
