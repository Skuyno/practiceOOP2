package exceptions;

public class DifferentLengthOfArraysException extends RuntimeException {
    // Конструктор без параметров
    public DifferentLengthOfArraysException() {
        super();
    }

    // Конструктор с сообщением
    public DifferentLengthOfArraysException(String message) {
        super(message);
    }
}
