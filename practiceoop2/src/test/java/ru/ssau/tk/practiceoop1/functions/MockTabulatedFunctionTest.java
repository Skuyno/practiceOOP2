package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MockTabulatedFunctionTest {

    @Test
    public void testInterpolate() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        double interpolatedValue = mockFunction.interpolate(0.5, 0);
        assertEquals(interpolatedValue, 0.5, 0.0001);

        double interpolatedValueAtX0 = mockFunction.interpolate(0.0, 0);
        assertEquals(interpolatedValueAtX0, 0.0, 0.0001);

        double interpolatedValueAtX1 = mockFunction.interpolate(1.0, 0);
        assertEquals(interpolatedValueAtX1, 1.0, 0.0001);
    }

    @Test
    public void testApply() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Тестирование экстраполяции слева
        assertEquals(mockFunction.apply(-1.0), 0.0, 0.0001);

        // Тестирование экстраполяции справа
        assertEquals(mockFunction.apply(2.0), 1.0, 0.0001);

        // Тестирование интерполяции внутри диапазона
        assertEquals(mockFunction.apply(0.5), 0.5, 0.0001);

        // Тестирование точек, соответствующих x0 и x1
        assertEquals(mockFunction.apply(0.0), 0.0, 0.0001);
        assertEquals(mockFunction.apply(1.0), 1.0, 0.0001);
    }

    @Test
    public void testIndexOfX() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверка индексов для точек, присутствующих в массиве xValues
        assertEquals(mockFunction.indexOfX(0.0), 0);
        assertEquals(mockFunction.indexOfX(1.0), 1);

        // Проверка для точки, которой нет в массиве xValues
        assertEquals(mockFunction.indexOfX(0.5), -1);
        assertEquals(mockFunction.indexOfX(-1.0), -1);
        assertEquals(mockFunction.indexOfX(2.0), -1);
    }

    @Test
    public void testIndexOfY() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверка индексов для точек, присутствующих в массиве yValues
        assertEquals(mockFunction.indexOfY(0.0), 0);
        assertEquals(mockFunction.indexOfY(1.0), 1);

        // Проверка для точки, которой нет в массиве yValues
        assertEquals(mockFunction.indexOfY(0.5), -1);
        assertEquals(mockFunction.indexOfY(-1.0), -1);
        assertEquals(mockFunction.indexOfY(2.0), -1);
    }
}