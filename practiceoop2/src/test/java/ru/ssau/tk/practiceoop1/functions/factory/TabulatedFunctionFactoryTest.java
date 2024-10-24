package ru.ssau.tk.practiceoop1.functions.factory;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TabulatedFunctionFactoryTest {

    private final double[] xValues = {1.0, 2.0, 3.0};
    private final double[] yValues = {2.0, 4.0, 6.0};

    @Test
    public void testCreateUnmodifiable() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction unmodifiableArrayFunction = arrayFactory.createUnmodifiable(xValues, yValues);

        assertEquals(3, unmodifiableArrayFunction.getCount());
        assertEquals(2.0, unmodifiableArrayFunction.getY(0));

        // Проверка, что функция действительно неизменяема
        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableArrayFunction.setY(0, 5.0);
        });

        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction unmodifiableLinkedListFunction = linkedListFactory.createUnmodifiable(xValues, yValues);

        assertEquals(3, unmodifiableLinkedListFunction.getCount());
        assertEquals(4.0, unmodifiableLinkedListFunction.getY(1));

        // Проверка на неизменяемость
        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableLinkedListFunction.setY(1, 7.0);
        });
    }
}
