package example.baseproject.module.user.controller;

import example.baseproject.module.user.model.User;
import example.baseproject.module.user.response.UserResponse;
import example.baseproject.module.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static example.baseproject.shared.constant.StatusCodeConstant.SUCCESS;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAuthenticatedUser() throws Exception {
        // Mock user response
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("email@example.com");
        mockUser.setFullName("Full Name");
        when(authentication.getPrincipal()).thenReturn(mockUser);

        UserResponse mockUserResponse = new UserResponse(1L, "email@example.com", "Full Name", null, null);
        when(userService.convertUserResponse(any(User.class))).thenReturn(mockUserResponse);

        // Perform GET request to /api/users/me
        mockMvc.perform(get("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS))
                .andExpect(jsonPath("$.message").value("Get user successful"))
                .andExpect(jsonPath("$.data.email").value("email@example.com"))
                .andExpect(jsonPath("$.data.fullName").value("Full Name"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Mock list of users
        UserResponse mockUser1 = new UserResponse(1L, "user1@example.com", "User One", null, null);
        UserResponse mockUser2 = new UserResponse(2L, "user2@example.com", "User Two", null, null);
        List<UserResponse> mockUsers = List.of(mockUser1, mockUser2);
        when(userService.allUsers()).thenReturn(mockUsers);

        // Perform GET request to /api/users
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS))
                .andExpect(jsonPath("$.message").value("Get list users successful"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$.data[1].email").value("user2@example.com"));
    }
}