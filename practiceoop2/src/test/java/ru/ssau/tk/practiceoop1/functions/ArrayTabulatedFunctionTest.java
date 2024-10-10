package ru.ssau.tk.practiceoop1.functions;

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
}