package ru.ssau.tk.practiceoop1.db.exceptions;

public class PointNotFoundException extends RuntimeException {
    public PointNotFoundException(Long id) {
        super("Point not found with id: " + id);
    }
}

