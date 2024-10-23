package ru.ssau.tk.practiceoop1.functions;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.operations.LeftSteppingDifferentialOperator;
import ru.ssau.tk.practiceoop1.operations.MiddleSteppingDifferentialOperator;
import ru.ssau.tk.practiceoop1.operations.RightSteppingDifferentialOperator;

import static org.junit.jupiter.api.Assertions.*;

class DifferentialOperatorTest {

    @Test
    void testLeftSteppingDifferentialOperator() {
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();

        double derivativeAt1 = operator.derive().apply(1);
        assertEquals(2, derivativeAt1, 0.01); // Производная функции x^2 в точке 1 равна 2
    }

    @Test
    void testRightSteppingDifferentialOperator() {
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();

        double derivativeAt1 = operator.derive().apply(1);
        assertEquals(2, derivativeAt1, 0.01); // Производная функции x^2 в точке 1 равна 2
    }

    @Test
    void testMiddleSteppingDifferentialOperator() {
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();

        double derivativeAt1 = operator.derive().apply(1);
        assertEquals(2, derivativeAt1, 0.01); // Производная функции x^2 в точке 1 равна 2
    }
}