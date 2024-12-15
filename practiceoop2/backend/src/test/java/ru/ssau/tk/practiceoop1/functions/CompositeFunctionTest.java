package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void testSimpleCompositeFunction() {
        // Создаем простые функции
        MathFunction f = x -> x + 1;
        MathFunction g = x -> x * 2;
        MathFunction h = x -> x - 3;

        // Создаем сложную функцию f.andThen(g).andThen(h)
        MathFunction composite = f.andThen(g).andThen(h);

        // Применяем сложную функцию к значению x
        double x = 2;
        double result = composite.apply(x);

        // Проверяем результат
        double expectedResult = (x + 1) * 2 - 3;
        assertEquals(expectedResult, result);
    }

    @Test
    void testAnonymousCompositeFunction() {
        // Создаем сложную функцию f.andThen(g).andThen(h) с использованием анонимных функций
        MathFunction composite = ((MathFunction) x -> x + 1)
                .andThen(x -> x * 2)
                .andThen(x -> x - 3);

        // Применяем сложную функцию к значению x
        double x = 2;
        double result = composite.apply(x);

        // Проверяем результат
        double expectedResult = (x + 1) * 2 - 3;
        assertEquals(expectedResult, result);
    }

    @Test
    void testMultipleCompositeFunction() {
        // Создаем простые функции
        MathFunction f = x -> x + 1;
        MathFunction g = x -> x * 2;
        MathFunction h = x -> x - 3;

        // Создаем сложную функцию f.andThen(g).andThen(h) с использованием нескольких вызовов andThen
        MathFunction composite = f.andThen(g).andThen(h);

        // Применяем сложную функцию к значению x
        double x = 2;
        double result = composite.apply(x);

        // Проверяем результат
        double expectedResult = (x + 1) * 2 - 3;
        assertEquals(expectedResult, result);
    }

    @Test
    void testCompositeWithArrayAndLinkedListTabulatedFunctions() {
        double[] xValuesArray = {1, 2, 3, 4, 5};
        double[] yValuesArray = {2, 4, 6, 8, 10};
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(xValuesArray, yValuesArray);

        double[] xValuesList = {2, 4, 6, 8, 10};
        double[] yValuesList = {1, 2, 3, 4, 5};
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(xValuesList, yValuesList);

        CompositeFunction composite = new CompositeFunction(arrayFunc, listFunc);

        assertEquals(3, composite.apply(3));
    }

    @Test
    void testCompositeFunctionWithTabulatedAndSqrFunction() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction tabulatedFunc = new LinkedListTabulatedFunction(xValues, yValues);

        MathFunction sqrFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(tabulatedFunc, sqrFunction);

        double expected = Math.pow(3, 4);
        assertEquals(expected, composite.apply(3));
    }

    @Test
    void testCompositeFunctionWithArrayTabulatedFunctionAndSqrFunction() {
        double[] xValues = {0, 1, 2, 3, 4};
        double[] yValues = {0, 1, 4, 9, 16};
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(xValues, yValues);

        MathFunction sqrFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(arrayFunc, sqrFunction);

        double expected = Math.pow(3, 4);
        assertEquals(expected, composite.apply(3));
    }
}