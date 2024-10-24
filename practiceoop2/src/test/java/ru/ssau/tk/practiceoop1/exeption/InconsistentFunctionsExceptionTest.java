import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.InconsistentFunctionsException;

import static org.junit.jupiter.api.Assertions.*;

class InconsistentFunctionsExceptionTest {

    @Test
    void testDefaultConstructor() {
        InconsistentFunctionsException exception = new InconsistentFunctionsException();
        assertNotNull(exception); // Проверка, что исключение не равно null
        assertNull(exception.getMessage()); // Проверка, что сообщение по умолчанию равно null
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Functions are inconsistent!";
        InconsistentFunctionsException exception = new InconsistentFunctionsException(message);
        assertNotNull(exception); // Проверка, что исключение не равно null
        assertEquals(message, exception.getMessage()); // Проверка, что сообщение соответствует переданному
    }
}
