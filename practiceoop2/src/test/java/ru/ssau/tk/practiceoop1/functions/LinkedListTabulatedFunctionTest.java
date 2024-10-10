package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionTest {

    @Test
    public void testGetXandGetY() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка значений X
        assertEquals(0.0, function.getX(0));
        assertEquals(1.0, function.getX(1));
        assertEquals(2.0, function.getX(2));

        // Проверка значений Y
        assertEquals(0.0, function.getY(0));
        assertEquals(1.0, function.getY(1));
        assertEquals(4.0, function.getY(2));
    }

    @Test
    public void testSetY() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Устанавливаем новое значение
        function.setY(1, 5.0);
        assertEquals(5.0, function.getY(1));
    }

    @Test
    public void testApplyExactValues() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка точного совпадения
        assertEquals(0.0, function.apply(0.0));
        assertEquals(1.0, function.apply(1.0));
        assertEquals(4.0, function.apply(2.0));
    }

    @Test
    public void testApplyInterpolation() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка интерполяции (между 1.0 и 2.0)
        assertEquals(2.5, function.apply(1.5));
    }

    @Test
    public void testApplyExtrapolationLeft() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка экстраполяции влево
        assertEquals(-2.0, function.apply(0.0));
    }

    @Test
    public void testApplyExtrapolationRight() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка экстраполяции вправо
        assertEquals(14.0, function.apply(4.0));
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка поиска индекса по значению X
        assertEquals(0, function.indexOfX(1.0));
        assertEquals(-1, function.indexOfX(4.0));
    }

    @Test
    public void testIndexOfY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка поиска индекса по значению Y
        assertEquals(0, function.indexOfY(1.0));
        assertEquals(-1, function.indexOfY(16.0));
    }

    @Test
    public void testLeftBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка левой границы
        assertEquals(1.0, function.leftBound());
    }

    @Test
    public void testRightBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка правой границы
        assertEquals(3.0, function.rightBound());
    }

    @Test
    public void testFloorIndexOfX() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Проверка на значения в пределах массива
        assertEquals(1, function.floorIndexOfX(1.5));
        assertEquals(2, function.floorIndexOfX(2.5));

        // Проверка на значения ниже минимального X
        assertEquals(0, function.floorIndexOfX(-1.0));

        // Проверка на значения выше максимального X
        assertEquals(3, function.floorIndexOfX(10.0));
    }

    @Test // Тут делаем финальную проверку большинства методов
    public void TestApply(){
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(0.0, function.apply(0.0));
        assertEquals(1.0, function.apply(1.0));
        assertEquals(4.0, function.apply(2.0));
        assertEquals(9.0, function.apply(3.0));

        // Проверка интерполяций
        assertEquals(2.5, function.apply(1.5));
        assertEquals(0.5, function.apply(0.5));
        assertEquals(6.5, function.apply(2.5));

        // Слева
        assertEquals(-1.0, function.apply(-1.0));
        assertEquals(-2.0, function.apply(-2.0));

        // Справа
        assertEquals(14.0, function.apply(4.0));
        assertEquals(19.0, function.apply(5.0));
    }

    @Test
    public void testRemove() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Удаление среднего элемента
        function.remove(2);
        assertEquals(4, function.getCount());
        assertEquals(4.0, function.getX(2));
        assertEquals(8.0, function.getY(2));

        // Удаление головы
        function.remove(0);
        assertEquals(3, function.getCount());
        assertEquals(2.0, function.getX(0));
        assertEquals(4.0, function.getY(0));

        // Удаление последнего элемента
        function.remove(function.getCount() - 1);
        assertEquals(2, function.getCount());
        assertEquals(4.0, function.getX(1));
        assertEquals(8.0, function.getY(1));
    }

}