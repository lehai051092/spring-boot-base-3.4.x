package example.baseproject.module.user.dto;

import example.baseproject.shared.validation.interfaces.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {

    @NotBlank(message = "email is required.")
    @UniqueEmail
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    @NotBlank(message = "fullName is required.")
    private String fullName;
}
