package ru.ssau.tk.practiceoop1.exception;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import static org.junit.jupiter.api.Assertions.*;

class InterpolationExceptionTest {

    @Test
    void testDefaultConstructor() {
        InterpolationException exception = new InterpolationException();
        assertNotNull(exception); // Проверка, что исключение не равно null
        assertNull(exception.getMessage()); // Проверка, что сообщение по умолчанию равно null
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Interpolation error occurred!";
        InterpolationException exception = new InterpolationException(message);
        assertNotNull(exception); // Проверка, что исключение не равно null
        assertEquals(message, exception.getMessage()); // Проверка, что сообщение соответствует переданному
    }
}
