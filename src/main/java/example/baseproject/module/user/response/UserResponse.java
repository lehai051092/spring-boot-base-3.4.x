package example.baseproject.module.user.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String email;

    private String fullName;

    private Date createdAt;

    private Date updatedAt;

    public UserResponse(Long id, String email, String fullName, Date createdAt, Date updatedAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
