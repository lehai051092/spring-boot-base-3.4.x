package example.baseproject.module.user.service;

import example.baseproject.module.user.model.User;
import example.baseproject.module.user.repository.UserRepository;
import example.baseproject.module.user.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "allUsers")
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

    @Cacheable(value = "users", key = "#userId")
    public UserResponse getUserById(Long userId) {
        logger.info("Fetching user with ID: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with ID: {}", userId);
            return new RuntimeException("User not found");
        });

        return this.convertUserResponse(user);
    }

    public User findUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);

        return userRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("User not found with email: {}", email);
            return new RuntimeException("User not found");
        });
    }

    public UserResponse updatePasswordByEmail(String email) {
        User user = this.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return this.convertUserResponse(user);
    }
}
