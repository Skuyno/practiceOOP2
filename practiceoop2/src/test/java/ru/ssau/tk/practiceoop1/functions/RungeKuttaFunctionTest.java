package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RungeKuttaFunctionTest {

    @Test
    void testRungeKuttaFunction() {
        // Начальные условия
        double x0 = 0; // Начальное значение x
        double y0 = 1; // Начальное значение y
        double h = 0.01; // Шаг интегрирования

        // Создаем объект RungeKuttaFunction
        RungeKuttaFunction rungeKuttaFunction = new RungeKuttaFunction(x0, y0, h);

        // Проверяем значения в разных точках
        double resultAtX1 = rungeKuttaFunction.apply(0.1);
        double expectedAtX1 = 1.1103418361330284; // Ожидаемое значение (можно рассчитать заранее)
        assertEquals(expectedAtX1, resultAtX1, 1e-10, "Результат в точке x=0.1 неверен");

        double resultAtX2 = rungeKuttaFunction.apply(0.2);
        double expectedAtX2 = 1.2428055162799636; // Ожидаемое значение
        assertEquals(expectedAtX2, resultAtX2, 1e-10, "Результат в точке x=0.2 неверен");

        double resultAtX3 = rungeKuttaFunction.apply(0.5);
        double expectedAtX3 = 1.7974425412640018; // Ожидаемое значение
        assertEquals(expectedAtX3, resultAtX3, 1e-10, "Результат в точке x=0.5 неверен");
    }
}