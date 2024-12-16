package ru.ssau.tk.practiceoop1.db.exceptions;

public class MathFunctionNotFoundException extends RuntimeException {
    public MathFunctionNotFoundException(Long id) {
        super("Function not found with id: " + id);
    }
}

