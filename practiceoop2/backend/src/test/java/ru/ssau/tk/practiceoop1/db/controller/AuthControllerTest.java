package ru.ssau.tk.practiceoop1.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.tk.practiceoop1.db.model.UserEntity;
import ru.ssau.tk.practiceoop1.db.model.UserRole;
import ru.ssau.tk.practiceoop1.db.repositories.UserRepository;
import ru.ssau.tk.practiceoop1.db.security.JwtResponse;
import ru.ssau.tk.practiceoop1.db.security.JwtTokenProvider;
import ru.ssau.tk.practiceoop1.db.security.LoginRequest;
import ru.ssau.tk.practiceoop1.db.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String BASE_URL = "/api/auth/";

    @Test
    public void testRegisterUserSuccess() throws Exception {
        String username = "testuser1";
        String password = "Test@123";
        String role = "USER";

        mockMvc.perform(post(BASE_URL + "register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));

        UserEntity user = userService.findByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
    }

    @Test
    public void testRegisterUserWithExistingUsername() throws Exception {
        String existingUsername = "testuser2";
        String password = "Test@123";
        String role = "USER";

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername(existingUsername);
        existingUser.setPassword(password);
        existingUser.setRole(UserRole.USER);
        userService.register(existingUser);

        mockMvc.perform(post(BASE_URL + "register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + existingUsername + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Username is already taken!"));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        String username = "testuser3";
        String password = "Test@123";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);

        userService.register(user);

        assertNotNull(userRepository.findByUsername(username));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testAuthenticateUserWithInvalidCredentials() throws Exception {
        String username = "testuser4";
        String password = "Test@123";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);
        userService.register(user);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"WrongPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Invalid credentials!"));
    }

    @Test
    public void testDeleteUserSuccessWithJwt() throws Exception {
        String username = "testuser5";
        String password = "Test@123";
        String role = "USER";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role+ "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));

        LoginRequest loginRequest = new LoginRequest(username, password);

        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        String jwtToken = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(jwtToken, JwtResponse.class);
        String token = jwtResponse.getToken();

        mockMvc.perform(delete("/api/auth/delete/" + username)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"));

        assertFalse(userRepository.existsByUsername(username));
    }
}
