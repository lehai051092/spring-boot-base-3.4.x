package example.baseproject.module.user.service;

import example.baseproject.module.user.model.User;
import example.baseproject.module.user.repository.UserRepository;
import example.baseproject.module.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> allUsers() {
        return this.convertListUserResponse(new ArrayList<>(userRepository.findAll()));
    }

    public List<UserResponse> convertListUserResponse(List<User> userList) {
        return userList.stream()
                .map(this::convertUserResponse)
                .toList();
    }

    public UserResponse convertUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
