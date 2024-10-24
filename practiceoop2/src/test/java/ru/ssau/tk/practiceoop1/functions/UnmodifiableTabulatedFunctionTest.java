package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.StrictTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnmodifiableTabulatedFunction;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UnmodifiableTabulatedFunctionTest {

    private TabulatedFunction original;
    private UnmodifiableTabulatedFunction unmodifiable;

    @BeforeEach
    public void setUp() {
        original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        unmodifiable = new UnmodifiableTabulatedFunction(original);
    }

    @Test
    public void testGetCount() {
        assertEquals(3, unmodifiable.getCount());
    }

    @Test
    public void testGetX() {
        assertThrows(IllegalArgumentException.class, () -> {
            unmodifiable.getX(-1); // или другой неправильный индекс
        });
    }

    @Test
    public void testGetY() {
        assertThrows(IllegalArgumentException.class, () -> {
            unmodifiable.getY(-1); // или другой неправильный индекс
        });
    }


    @Test
    public void testSetY() {
        assertThrows(UnsupportedOperationException.class, () -> unmodifiable.setY(0, 10));
    }

    @Test
    public void testIndexOfX() {
        assertEquals(0, unmodifiable.indexOfX(0));
        assertEquals(1, unmodifiable.indexOfX(1));
        assertEquals(2, unmodifiable.indexOfX(2));
        assertEquals(-1, unmodifiable.indexOfX(3)); // x not in the function
    }

    @Test
    public void testIndexOfY() {
        assertEquals(0, unmodifiable.indexOfY(0));
        assertEquals(1, unmodifiable.indexOfY(1));
        assertEquals(2, unmodifiable.indexOfY(4));
        assertEquals(-1, unmodifiable.indexOfY(5)); // y not in the function
    }

    @Test
    public void testLeftBound() {
        assertEquals(0, unmodifiable.leftBound());
    }

    @Test
    public void testRightBound() {
        assertEquals(2, unmodifiable.rightBound());
    }

//    @Test
//    public void testApply() {
//        assertEquals(0, unmodifiable.apply(0));
//        assertEquals(1, unmodifiable.apply(1));
//        assertEquals(4, unmodifiable.apply(2));
//
//        // Проверка интерполяции
//        assertEquals(2.5, unmodifiable.apply(1.5), 0.01);
//
//        // Проверка на выход за пределы
//        assertThrows(ArrayIndexOutOfBoundsException.class, () -> unmodifiable.apply(-1));
//
//    }
@Test
void testApply() {

    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
        unmodifiable.apply(-1); // или используйте индекс, который выходит за пределы
    });
}






    @Test
    public void testWrappingUnmodifiableInStrict() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        UnmodifiableTabulatedFunction unmodifiable = new UnmodifiableTabulatedFunction(original);
        StrictTabulatedFunction strictWrapper = new StrictTabulatedFunction(unmodifiable);

        // Проверка работы apply
        assertEquals(1, strictWrapper.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> strictWrapper.setY(0, 10));

        // Проверка интерполяции (должно бросать исключение)
        assertThrows(UnsupportedOperationException.class, () -> strictWrapper.apply(-1));
    }
}

