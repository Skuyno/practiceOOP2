package ru.ssau.tk.practiceoop1.operations;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.MathFunction;
import ru.ssau.tk.practiceoop1.functions.SqrFunction;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DifferentialOperatorTest {

    private final MathFunction sqrFunction = x -> x * x;

    @Test
    void testLeftSteppingDifferentialOperator() {
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.01);
        MathFunction derivative = operator.derive(sqrFunction);

        assertEquals(2.0, derivative.apply(1.0), 0.02); // f'(1) = 2
        assertEquals(0.0, derivative.apply(0.0), 0.02); // f'(0) = 0
    }

    @Test
    void testRightSteppingDifferentialOperator() {
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(0.01);
        MathFunction derivative = operator.derive(sqrFunction);

        assertEquals(2.0, derivative.apply(1.0), 0.02); // f'(1) = 2
        assertEquals(0.0, derivative.apply(0.0), 0.02); // f'(0) = 0
    }

    @Test
    void testMiddleSteppingDifferentialOperator() {
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(0.01);
        MathFunction derivative = operator.derive(sqrFunction);
        assertEquals(2.0, derivative.apply(1.0), 0.01); // f'(1) = 2
        assertEquals(0.0, derivative.apply(0.0), 0.01); // f'(0) = 0
    }

    @Test
    void testInvalidStep() {
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(-1));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(0));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.NaN));
    }
}