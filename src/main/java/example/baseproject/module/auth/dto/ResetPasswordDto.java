package example.baseproject.module.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}
