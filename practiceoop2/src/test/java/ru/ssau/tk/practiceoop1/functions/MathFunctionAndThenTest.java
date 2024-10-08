package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathFunctionAndThenTest {

    @Test
    void testAndThenWithSimpleFunctions() {
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
    void testAndThenWithAnonymousFunctions() {
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
    void testAndThenWithMultipleAndThenCalls() {
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
}