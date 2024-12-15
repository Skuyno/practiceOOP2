package ru.ssau.tk.practiceoop1.exception;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.ArrayIsNotSortedException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIsNotSortedExceptionTest {

    @Test
    void testDefaultConstructor() {
        ArrayIsNotSortedException exception = new ArrayIsNotSortedException();
        assertNotNull(exception);
        assertNull(exception.getMessage()); // Проверка, что сообщение по умолчанию равно null
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Array is not sorted!";
        ArrayIsNotSortedException exception = new ArrayIsNotSortedException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage()); // Проверка, что сообщение соответствует переданному
    }
}