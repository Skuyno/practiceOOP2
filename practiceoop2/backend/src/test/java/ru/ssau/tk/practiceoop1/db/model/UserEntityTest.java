package ru.ssau.tk.practiceoop1.db.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity userEntity;
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Создаем фабрику валидаторов
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Создаем экземпляр UserEntity с корректными данными
        userEntity = new UserEntity(1, "testUser", "testPassword", UserRole.USER);
    }

    @Test
    void testConstructor() {
        UserEntity user = new UserEntity(2, "anotherUser", "anotherPassword", UserRole.ADMIN);
        assertEquals(2, user.getId());
        assertEquals("anotherUser", user.getUsername());
        assertEquals("anotherPassword", user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void testGetId() {
        assertEquals(1, userEntity.getId());
    }

    @Test
    void testSetId() {
        userEntity.setId(2);
        assertEquals(2, userEntity.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", userEntity.getUsername());
    }

    @Test
    void testSetUsername() {
        userEntity.setUsername("newUser");
        assertEquals("newUser", userEntity.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("testPassword", userEntity.getPassword());
    }

    @Test
    void testSetPassword() {
        userEntity.setPassword("newPassword");
        assertEquals("newPassword", userEntity.getPassword());
    }

    @Test
    void testGetRole() {
        assertEquals(UserRole.USER, userEntity.getRole());
    }

    @Test
    void testSetRole() {
        userEntity.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, userEntity.getRole());
    }

    @Test
    void testValidUserEntity() {
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
        assertTrue(violations.isEmpty(), "UserEntity should be valid");
    }

    @Test
    void testInvalidUsername() {
        userEntity.setUsername(null);
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to null username");

        userEntity.setUsername("");
        violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to blank username");
    }

    @Test
    void testInvalidPassword() {
        userEntity.setPassword(null);
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to null password");

        userEntity.setPassword("");
        violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to blank password");

        userEntity.setPassword("short");
        violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to short password");

        // Test for maximum length password
        userEntity.setPassword("a".repeat(256)); // 256 characters
        violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to long password");

        // Valid max length password
        userEntity.setPassword("a".repeat(255)); // 255 characters
        violations = validator.validate(userEntity);
        assertTrue(violations.isEmpty(), "UserEntity should be valid with maximum length password");
    }

    @Test
    void testInvalidRole() {
        userEntity.setRole(null);
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
        assertFalse(violations.isEmpty(), "UserEntity should be invalid due to null role");
    }

    @Test
    void testToString() {
        String expectedString = "UserEntity(id=1, username=testUser, password=testPassword, role=USER)";
        assertEquals(expectedString, userEntity.toString());

        userEntity.setUsername("newUser");
        expectedString = "UserEntity(id=1, username=newUser, password=testPassword, role=USER)";
        assertEquals(expectedString, userEntity.toString());
    }
}
