package ru.ssau.tk.practiceoop1.functions.factory;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionFactoryTest {

    @Test
    void testCreateInstanceOf() {
        TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};

        TabulatedFunction function = factory.create(xValues, yValues);

        assertNotNull(function);
        assertInstanceOf(LinkedListTabulatedFunction.class, function);
    }
}