package ru.ssau.tk.practiceoop1.db.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User not found with username: " + message);
    }
}
