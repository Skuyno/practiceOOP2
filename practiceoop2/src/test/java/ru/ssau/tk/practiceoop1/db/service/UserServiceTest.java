package ru.ssau.tk.practiceoop1.db.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ssau.tk.practiceoop1.db.model.UserEntity;
import ru.ssau.tk.practiceoop1.db.model.UserRole;
import ru.ssau.tk.practiceoop1.db.repositories.UserRepository;
import ru.ssau.tk.practiceoop1.db.security.CustomUserDetails;
import ru.ssau.tk.practiceoop1.db.exceptions.UserNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity(1, "testUser", "testPassword", UserRole.USER);
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.existsByUsername(userEntity.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity registeredUser = userService.register(userEntity);

        assertEquals(userEntity.getUsername(), registeredUser.getUsername());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUsernameAlreadyTaken() {
        when(userRepository.existsByUsername(userEntity.getUsername())).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.register(userEntity));
        assertEquals("Username is already taken!", thrown.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testFindByUsernameSuccess() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        UserEntity foundUser = userService.findByUsername(userEntity.getUsername());

        assertEquals(userEntity.getUsername(), foundUser.getUsername());
        verify(userRepository).findByUsername(userEntity.getUsername());
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.findByUsername(userEntity.getUsername()));

        assertEquals("User not found with username: testUser", thrown.getMessage());

        verify(userRepository).findByUsername(userEntity.getUsername());
    }


    @Test
    void testDeleteSuccess() {
        doNothing().when(userRepository).deleteByUsername(userEntity.getUsername());

        assertDoesNotThrow(() -> userService.delete(userEntity.getUsername()));
        verify(userRepository).deleteByUsername(userEntity.getUsername());
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        CustomUserDetails customUserDetails = userService.loadUserByUsername(userEntity.getUsername());

        assertNotNull(customUserDetails);
        assertEquals(userEntity.getUsername(), customUserDetails.getUsername());
        verify(userRepository).findByUsername(userEntity.getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(userEntity.getUsername()));
        assertEquals("User not found with username: testUser", thrown.getMessage());
        verify(userRepository).findByUsername(userEntity.getUsername());
    }
}
