package exceptions;

public class ArrayIsNotSortedException extends RuntimeException {
    // Конструктор без параметров
    public ArrayIsNotSortedException() {
        super();
    }

    // Конструктор с сообщением
    public ArrayIsNotSortedException(String message) {
        super(message);
    }
}
