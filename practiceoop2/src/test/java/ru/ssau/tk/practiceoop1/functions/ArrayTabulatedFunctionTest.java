package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {
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
}