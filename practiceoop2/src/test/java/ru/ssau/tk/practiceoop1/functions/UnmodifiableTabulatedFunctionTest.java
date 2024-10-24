package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.StrictTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnmodifiableTabulatedFunction;

import static org.junit.jupiter.api.Assertions.*;

public class UnmodifiableTabulatedFunctionTest {

    @Test
    public void testUnmodifiableFunction() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        UnmodifiableTabulatedFunction unmodifiable = new UnmodifiableTabulatedFunction(original);

        // Проверка значений
        assertEquals(0, unmodifiable.getY(0));
        assertEquals(1, unmodifiable.getY(1));
        assertEquals(4, unmodifiable.getY(2));

        // Проверка размера
        assertEquals(3, unmodifiable.getCount());

        // Проверка apply
        assertEquals(1, unmodifiable.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> unmodifiable.setY(0, 10));
    }

    @Test
    public void testWrappingStrictInUnmodifiable() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        StrictTabulatedFunction strict = new StrictTabulatedFunction(original);
        UnmodifiableTabulatedFunction unmodifiableStrict = new UnmodifiableTabulatedFunction(strict);

        // Проверка работы apply
        assertEquals(1, unmodifiableStrict.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrict.setY(0, 10));

        // Проверка интерполяции (должно работать)
        assertEquals(1, unmodifiableStrict.apply(1.0), 0.001);

        // Проверка на наличие интерполяции для значения вне диапазона (должно бросать исключение)
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrict.apply(-1));
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
