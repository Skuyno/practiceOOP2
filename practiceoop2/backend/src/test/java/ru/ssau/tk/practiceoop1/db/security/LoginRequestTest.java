package ru.ssau.tk.practiceoop1.db.security;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginRequestTest {

    @Test
    public void testNoArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest();
        assertThat(loginRequest).isNotNull();
        assertThat(loginRequest.getUsername()).isNull();
        assertThat(loginRequest.getPassword()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        String username = "testUser";
        String password = "testPass";
        LoginRequest loginRequest = new LoginRequest(username, password);

        assertThat(loginRequest).isNotNull();
        assertThat(loginRequest.getUsername()).isEqualTo(username);
        assertThat(loginRequest.getPassword()).isEqualTo(password);
    }

    @Test
    public void testSettersAndGetters() {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername("newUser");
        loginRequest.setPassword("newPass");

        assertThat(loginRequest.getUsername()).isEqualTo("newUser");
        assertThat(loginRequest.getPassword()).isEqualTo("newPass");
    }

    @Test
    public void testEqualsAndHashCode() {
        LoginRequest loginRequest1 = new LoginRequest("user1", "pass1");
        LoginRequest loginRequest2 = new LoginRequest("user1", "pass1");

        assertThat(loginRequest1).isEqualTo(loginRequest2);
        assertThat(loginRequest1.hashCode()).isEqualTo(loginRequest2.hashCode());

        loginRequest2.setPassword("pass2");

        assertThat(loginRequest1).isNotEqualTo(loginRequest2);
    }

    @Test
    public void testToString() {
        LoginRequest loginRequest = new LoginRequest("user", "pass");

        String expectedString = "LoginRequest(username=user, password=pass)";
        assertThat(loginRequest.toString()).contains("username=user");
        assertThat(loginRequest.toString()).contains("password=pass");
    }
}

