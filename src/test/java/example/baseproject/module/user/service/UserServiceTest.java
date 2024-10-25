package example.baseproject.module.user.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import example.baseproject.module.user.repository.UserRepository;
import example.baseproject.module.user.model.User;
import example.baseproject.module.user.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        // Setup mock behavior
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("email@example.com");
        mockUser.setFullName("fullName");
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setUpdatedAt(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Call the method to test
        UserResponse userResponse = userService.getUserById(1L);

        // Verify the result
        assertNotNull(userResponse);
        assertEquals("fullName", userResponse.getFullName());
    }
}
