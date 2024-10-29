import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.DifferentLengthOfArraysException;

import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthOfArraysExceptionTest {

    @Test
    void testDefaultConstructor() {
        DifferentLengthOfArraysException exception = new DifferentLengthOfArraysException();
        assertNotNull(exception);
        assertNull(exception.getMessage()); // Проверка, что сообщение по умолчанию равно null
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Arrays have different lengths!";
        DifferentLengthOfArraysException exception = new DifferentLengthOfArraysException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage()); // Проверка, что сообщение соответствует переданному
    }
}
