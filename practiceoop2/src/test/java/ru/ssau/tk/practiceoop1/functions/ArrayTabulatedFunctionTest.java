package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.practiceoop1.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {
    @Test
    public void testDifferentLengthOfArraysException() {
        double[] x = {1.0, 2.0};
        double[] y = {3.0};
        assertThrows(DifferentLengthOfArraysException.class, () -> {
            AbstractTabulatedFunction.checkLengthIsTheSame(x, y);
        });
    }

    @Test
    public void testArrayIsNotSortedException() {
        double[] x = {2.0, 1.0};
        assertThrows(ArrayIsNotSortedException.class, () -> {
            AbstractTabulatedFunction.checkSorted(x);
        });
    }
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
    void insertTest() {
        double[] xValues = {1.0, 3.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        // Вставка уже существующего
        function.insert(1.0, 0.5);
        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0));
        assertEquals(0.5, function.getY(0));

        // Вставка значения меньше всех существующих
        function.insert(0.0, 1.0);
        assertEquals(4, function.getCount());
        assertEquals(0.0, function.getX(1));
        assertEquals(1.0, function.getY(1));

        // Вставка значения между существующими
        function.insert(4.0, 5.0);
        assertEquals(5, function.getCount());
        assertEquals(4.0, function.getX(3));
        assertEquals(5.0, function.getY(3));

        // Вставка значения больше всех существующих
        function.insert(6.0, 7.0);
        assertEquals(6, function.getCount());
        assertEquals(6.0, function.getX(5));
        assertEquals(7.0, function.getY(5));
    }

    private ArrayTabulatedFunction function;

    @Test
    void testConstructorWithMathFunction() {
        // Тест на правильное построение с простым линейным источником
        MathFunction linearFunction = x -> 2 * x;  // Линейная функция: y = 2x
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0));
        assertEquals(0.0, function.getY(0));  // Ожидаем 0.0, так как y = 2 * 0 = 0
        assertEquals(10.0, function.getX(4));
        assertEquals(20.0, function.getY(4));  // Проверяем последнее значение
    }

    @BeforeEach
    public void setUp() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        function = new ArrayTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testRemoveMiddleElement() {
        function.remove(1); // Удаляем элемент с индексом 1 (x = 2.0)

        assertEquals(2, function.getCount());
        assertEquals(1.0, function.getX(0));
        assertEquals(3.0, function.getX(1)); // Теперь x = 3.0 на месте x = 1
        assertEquals(1.0, function.getY(0));
        assertEquals(9.0, function.getY(1)); // y = 9.0 на месте y = 1
    }

    @Test
    public void testRemoveFirstElement() {
        function.remove(0); // Удаляем первый элемент (x = 1.0)

        assertEquals(2, function.getCount());
        assertEquals(2.0, function.getX(0)); // Теперь x = 2.0 должен быть на месте 0
        assertEquals(4.0, function.getY(0)); // y = 4.0 теперь на месте 0
    }

    @Test
    public void testRemoveLastElement() {
        function.remove(2); // Удаляем последний элемент (x = 3.0)

        assertEquals(2, function.getCount());
        assertEquals(1.0, function.getX(0)); // x = 1.0 на месте 0
        assertEquals(4.0, function.getY(1)); // y = 4.0 должен остаться на месте
    }

    @Test
    public void testGetX() {
        assertEquals(1.0, function.getX(0));
        assertEquals(2.0, function.getX(1));
        assertEquals(3.0, function.getX(2));
    }

    @Test
    public void testGetY() {
        assertEquals(1.0, function.getY(0));
        assertEquals(4.0, function.getY(1));
    }

    @Test
    public void testSetY() {
        function.setY(0, 10.0);
        assertEquals(10.0, function.getY(0));
        function.setY(2, 12.0);
        assertEquals(12.0, function.getY(2));
    }

    @Test
    public void testLeftBound() {
        assertEquals(1.0, function.leftBound());
    }

    @Test
    public void testRightBound() {
        assertEquals(3.0, function.rightBound());
    }

    @Test
    public void testApply() {
        // Внутри диапазона
        assertEquals(1.0, function.apply(1.0), 0.0001);
        assertEquals(4.0, function.apply(2.0), 0.0001);
        assertEquals(9.0, function.apply(3.0), 0.0001);

        // Экстраполяция слева
        assertEquals(1.0, function.apply(0.5), 0.0001);

        // Экстраполяция справа
        assertEquals(9.0, function.apply(3.5), 0.0001);
    }

    @Test
    public void testInterpolate() {
        double interpolatedValue = function.interpolate(2.5, 1);
        assertEquals(6.5, interpolatedValue, 0.0001);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(0, function.indexOfX(1.0));
        assertEquals(1, function.indexOfX(2.0));
        assertEquals(-1, function.indexOfX(5.0));
    }

    @Test
    public void testIndexOfY() {
        assertEquals(0, function.indexOfY(1.0));
        assertEquals(1, function.indexOfY(4.0));
        assertEquals(-1, function.indexOfY(5.0));
    }
}