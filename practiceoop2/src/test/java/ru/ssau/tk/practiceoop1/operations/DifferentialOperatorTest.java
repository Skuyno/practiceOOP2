package ru.ssau.tk.practiceoop1.operations;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.MathFunction;
import ru.ssau.tk.practiceoop1.functions.SqrFunction;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DifferentialOperatorTest {

    @Test
    public void testLeftSteppingDifferentialOperator() {
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();
        MathFunction derivative = operator.derive(function);

        assertEquals(0, derivative.apply(0), 0.001);
        assertEquals(2, derivative.apply(1), 0.001);
        assertEquals(4, derivative.apply(2), 0.001);
    }

    @Test
    public void testRightSteppingDifferentialOperator() {
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();
        MathFunction derivative = operator.derive(function);

        assertEquals(2, derivative.apply(0), 0.001);
        assertEquals(4, derivative.apply(1), 0.001);
        assertEquals(6, derivative.apply(2), 0.001);
    }

    @Test
    public void testMiddleSteppingDifferentialOperator() {
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(0.01);
        MathFunction function = new SqrFunction();
        MathFunction derivative = operator.derive(function);

        assertEquals(2, derivative.apply(0), 0.001);
        assertEquals(4, derivative.apply(1), 0.001);
        assertEquals(6, derivative.apply(2), 0.001);
    }
}
