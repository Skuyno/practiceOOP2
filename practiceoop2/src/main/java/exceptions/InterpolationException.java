package exceptions;

public class InterpolationException extends RuntimeException {
    // Конструктор без параметров
    public InterpolationException() {
        super();
    }

    // Конструктор с сообщением
    public InterpolationException(String message) {
        super(message);
    }
}
