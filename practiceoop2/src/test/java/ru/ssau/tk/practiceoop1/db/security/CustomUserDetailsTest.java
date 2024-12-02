package ru.ssau.tk.practiceoop1.db.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import ru.ssau.tk.practiceoop1.db.model.UserEntity;
import ru.ssau.tk.practiceoop1.db.model.UserRole;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private UserEntity userEntity;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        userEntity = Mockito.mock(UserEntity.class);
        Mockito.when(userEntity.getId()).thenReturn(1);
        Mockito.when(userEntity.getUsername()).thenReturn("testUser");
        Mockito.when(userEntity.getPassword()).thenReturn("testPassword");
        Mockito.when(userEntity.getRole()).thenReturn(UserRole.USER); // предположим, что у вас есть enum UserRole

        customUserDetails = CustomUserDetails.build(userEntity);
    }

    @Test
    void testBuild() {
        assertNotNull(customUserDetails);
        assertEquals(1, customUserDetails.getId());
        assertEquals("testUser", customUserDetails.getUsername());
        assertEquals("testPassword", customUserDetails.getPassword());
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertFalse(authorities.isEmpty());
    }

    @Test
    void testGetPassword() {
        assertEquals("testPassword", customUserDetails.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", customUserDetails.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }
}

