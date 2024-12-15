package ru.ssau.tk.practiceoop1.concurrent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.SqrFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTaskExecutorTest {
    private IntegrationTaskExecutor executor;


    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    public void testIntegrateNormalCaseConstructorWithoutParameters() throws ExecutionException, InterruptedException {
        executor = new IntegrationTaskExecutor();

        // Создаем табулированную функцию y = x^2 на отрезке [0, 3]
        TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(new SqrFunction(), 0.0,3.0, 3001);

        // Вычисляем интеграл с 1000 шагами
        double integral = executor.integrate(tabulatedFunction, 1000);

        // Аналитический интеграл x^2 от 0 до 3 равен 9.0
        assertEquals(9.0, integral, 0.001, "Интеграл функции y = x^2 от 0 до 3 должен быть примерно 9.0");
    }

    @Test
    public void testIntegrateNormalCaseConstructorWithParameters() throws ExecutionException, InterruptedException {
        executor = new IntegrationTaskExecutor(2);

        // Создаем табулированную функцию y = x^2 на отрезке [0, 3]
        TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(new SqrFunction(), 0.0,3.0, 3001);

        // Вычисляем интеграл с 1000 шагами
        double integral = executor.integrate(tabulatedFunction, 1000);

        // Аналитический интеграл x^2 от 0 до 3 равен 9.0
        assertEquals(9.0, integral, 0.001, "Интеграл функции y = x^2 от 0 до 3 должен быть примерно 9.0");
    }

    @Test
    public void testIntegrationWithNegativeSegments() {
        executor = new IntegrationTaskExecutor();

        double[] xValues = {0.0, 1.0};
        double[] yValues = {0.0, 1.0};
        ArrayTabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IllegalArgumentException.class, () -> new IntegrationTask(tabulatedFunction, 0, 1,0));
    }

    @Test
    public void testIntegrationWithInvalidBounds() {
        executor = new IntegrationTaskExecutor();

        double[] xValues = {0.0, 1.0};
        double[] yValues = {0.0, 1.0};
        ArrayTabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IllegalArgumentException.class, () -> new IntegrationTask(tabulatedFunction, 1, 0,1000));
    }

}