package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import static org.junit.jupiter.api.Assertions.*;

public class MockTabulatedFunctionTest {

    @Test
    public void testInterpolationException() {
        double[] x = {1.0, 2.0, 3.0};
        double[] y = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(x, y);

        assertThrows(InterpolationException.class, () -> {
            function.interpolate(4.0,2);
        });
    }
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

    @Test
    public void testGetX() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверяем возвращаемые значения X
        assertEquals(0.0, mockFunction.getX(0));
        assertEquals(1.0, mockFunction.getX(1));
    }

    // Тесты для метода getY()
    @Test
    public void testGetY() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверяем возвращаемые значения Y
        assertEquals(0.0, mockFunction.getY(0));
        assertEquals(1.0, mockFunction.getY(1));
    }

    // Тесты для метода setY()
    @Test
    public void testSetY() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Изменение значений Y
        mockFunction.setY(0, 2.0);
        assertEquals(2.0, mockFunction.getY(0));

        mockFunction.setY(1, 3.0);
        assertEquals(3.0, mockFunction.getY(1));
    }

    // Тесты для методов leftBound() и rightBound()
    @Test
    public void testBounds() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверяем возвращаемые значения границ
        assertEquals(0.0, mockFunction.leftBound());
        assertEquals(1.0, mockFunction.rightBound());
    }

    @Test
    public void testGetCount() {
        MockTabulatedFunction mockFunction = new MockTabulatedFunction();

        // Проверяем, что количество точек равно 2
        assertEquals(2, mockFunction.getCount());
    }
}