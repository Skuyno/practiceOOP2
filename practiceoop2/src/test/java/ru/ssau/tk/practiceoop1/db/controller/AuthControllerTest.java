package ru.ssau.tk.practiceoop1.db.controller;

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
import ru.ssau.tk.practiceoop1.db.security.JwtTokenProvider;
import ru.ssau.tk.practiceoop1.db.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

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
    private JwtTokenProvider jwtTokenProvider;

    private static final String BASE_URL = "/api/auth/";

    @BeforeEach
    public void setUp() {
        // Очищаем базу данных перед каждым тестом
        userRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        // Очищаем базу данных после каждого теста
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Создаем нового пользователя
        String username = "testuser1";
        String password = "Test@123";
        String role = "USER";

        // Выполняем запрос на регистрацию пользователя
        mockMvc.perform(post(BASE_URL + "register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));

        // Проверяем, что пользователь был зарегистрирован в базе данных
        UserEntity user = userService.findByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
    }

    @Test
    public void testRegisterUserWithExistingUsername() throws Exception {
        // Создаем пользователя, который уже существует в базе
        String existingUsername = "testuser2";
        String password = "Test@123";
        String role = "USER";

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername(existingUsername);
        existingUser.setPassword(password);
        existingUser.setRole(UserRole.USER);
        userService.register(existingUser);

        // Выполняем запрос на регистрацию с уже существующим именем
        mockMvc.perform(post(BASE_URL + "register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + existingUsername + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Username is already taken!"));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        // Создаем пользователя для теста
        String username = "testuser3";
        String password = "Test@123";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);

        // Сохраняем пользователя в базу данных
        userService.register(user);

        // Проверяем, что пользователь был сохранен
        assertNotNull(userRepository.findByUsername(username));

        // Выполняем запрос на аутентификацию
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

        // Создаем пользователя для теста
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);
        userService.register(user);

        // Пытаемся авторизоваться с неправильным паролем
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"WrongPassword\"}"))
                .andExpect(status().isBadRequest())  // Ожидаем 400 Bad Request
                .andExpect(content().string("Error: Invalid credentials!"));
    }

    @Test
    @WithMockUser(username = "testuser5", roles = "USER")
    public void testDeleteUserSuccess() throws Exception {
        String username = "testuser5";
        String password = "Test@123";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);
        userService.register(user);

        mockMvc.perform(delete(BASE_URL + "delete/" + username))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"))
                .andReturn();
    }

}
