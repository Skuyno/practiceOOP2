package ru.ssau.tk.practiceoop1.exceptions;

public class InconsistentFunctionsException extends RuntimeException {
    public InconsistentFunctionsException() {
        super();
    }


    public InconsistentFunctionsException(String message) {
        super(message);
    }
}
