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

    @Test
    void testFindKnotIndex() {
        double[] controlPoints = {0, 1, 4, 9, 16};
        double[] knots = {0, 0, 1, 2, 3, 3};
        int degree = 2;
        DeBoorFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        // Проверка нахождения узла внутри интервала
        assertEquals(1.25, deBoorFunction.apply(1.5), 0.001);
    }

    @Test
    void testKnotBoundaryConditions() {
        double[] controlPoints = {0, 1, 4, 9, 16};
        double[] knots = {0, 0, 1, 2, 3, 3};
        int degree = 2;
        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        // Проверка на границах узлового вектора
        assertEquals(0.5, deBoorFunction.apply(0), 0.0001);
        assertEquals(6.5, deBoorFunction.apply(3), 0.0001);
    }

    @Test
    void testIsNonDecreasing() {
        double[] strictlyIncreasingArray = {1.0, 2.0, 3.0, 4.0};
        double[] nonDecreasingArray = {1.0, 1.0, 2.0, 2.0, 3.0};
        double[] decreasingArray = {4.0, 3.0, 2.0, 1.0};
        double[] emptyArray = {};
        double[] singleElementArray = {5.0};

        DeBoorFunction deBoorFunction = new DeBoorFunction(new double[]{}, new double[]{}, 0);

        // Проверяем неубывающие массивы
        assertTrue(deBoorFunction.isNonDecreasing(strictlyIncreasingArray));
        assertTrue(deBoorFunction.isNonDecreasing(nonDecreasingArray));

        // Проверяем убывающий массив
        assertFalse(deBoorFunction.isNonDecreasing(decreasingArray));

        // Проверяем пустой массив и массив с одним элементом
        assertTrue(deBoorFunction.isNonDecreasing(emptyArray));
        assertTrue(deBoorFunction.isNonDecreasing(singleElementArray));
    }

    @Test
    void testDegreeZero() {
        double[] controlPoints = {0, 1, 4, 9, 16};
        double[] knots = {0, 0, 1, 2, 3, 3};
        int degree = 0; // Степень нулевая, просто вывод контрольной точки

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        assertEquals(9, deBoorFunction.apply(2.5), 0.0001);
    }

    @Test
    void testHighDegreeSpline() {
        double[] controlPoints = {0, 1, 8, 27, 64, 125, 216};
        double[] knots = {0, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4};
        int degree = 3;

        MathFunction deBoorFunction = new DeBoorFunction(controlPoints, knots, degree);

        // Проверка высокой степени
        assertEquals(98.548977, deBoorFunction.apply(3.3244), 0.000001);
    }
}