package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeBoorFunctionTest {
    // Проверяем степени сплайна
    @Test
    void TestDegreeOne() {
        double[] controlPoints = {0, 1, 2, 3, 4, 5, 6};
        double[] knots = {0, 0, 1, 2, 3, 4, 5, 6, 6};
        int degree = 1;

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        double x = 5.6;
        double result = deBoorFunction.apply(x);

        assertEquals(5.6, result, 0.000001);
    }

    @Test
    void TestDegreeTwo() {
        double[] controlPoints = {0, 1, 4, 9, 16, 25};
        double[] knots = {0, 0, 0, 1, 2, 3, 4, 4, 4};
        int degree = 2;

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        double x = 3.2412;
        double result = deBoorFunction.apply(x);

        assertEquals(14.508375, result, 0.000001);
    }

    @Test
    void TestDegreeThree() {
        double[] controlPoints = {0, 1, 8, 27, 64, 125, 216};
        double[] knots = {0, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4};
        int degree = 3;

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        double x = 3.3244;
        double result = deBoorFunction.apply(x);

        assertEquals(98.548977, result, 0.000001);
    }

    @Test
    void TestDegreeFour() {
        double[] controlPoints = {0, 1, 16, 81, 256, 625, 1296, 2401};
        double[] knots = {0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4, 4};
        int degree = 4;

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        double x = 2.646;
        double result = deBoorFunction.apply(x);

        assertEquals(380.913220, result, 0.000001);
    }
}