package ru.ssau.tk.practiceoop1.db.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.practiceoop1.db.model.UserEntity;
import ru.ssau.tk.practiceoop1.db.security.JwtResponse;
import ru.ssau.tk.practiceoop1.db.security.JwtTokenProvider;
import ru.ssau.tk.practiceoop1.db.security.LoginRequest;
import ru.ssau.tk.practiceoop1.db.service.UserService;
import ru.ssau.tk.practiceoop1.db.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        userService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

            String jwt = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Error: Invalid credentials!");
        }
    }

    @DeleteMapping("delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username, @AuthenticationPrincipal UserDetails currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.badRequest().body("Error: You can only delete your own account.");
        }

        try {
            userService.delete(username); // Используем сервис для удаления
            return ResponseEntity.ok("User deleted successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Error: User not found");
        }
    }
}
