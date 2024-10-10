package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConstantFunctionTest {

    @Test
    void testApply() {
        MathFunction func1 = new ConstantFunction(5.0);
        MathFunction func2 = new ConstantFunction(-5.0);
        MathFunction func3 = new ConstantFunction(0);

        // Положительная константа
        assertEquals(5.0, func1.apply(0.0));
        assertEquals(5.0, func1.apply(-3.14));

        // Отрицательная
        assertEquals(-5.0, func2.apply(0.0));
        assertEquals(-5.0, func2.apply(100.0));

        //Ноль
        assertEquals(0, func3.apply(0.0));
        assertEquals(0, func3.apply(24.0));
    }
}