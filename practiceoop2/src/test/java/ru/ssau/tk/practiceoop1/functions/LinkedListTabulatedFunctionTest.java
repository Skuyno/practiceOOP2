package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
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
    void testConstructorWithMathFunction() {
        // Тест на правильное построение с простой линейной функцией
        MathFunction linearFunction = x -> 2 * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0));
        assertEquals(0.0, function.getY(0));
        assertEquals(10.0, function.getX(4));
        assertEquals(20.0, function.getY(4));

        // Проверяем шаг
        assertEquals(2.5, function.getX(1), 0.0001);
        assertEquals(5.0, function.getX(2), 0.0001);
        assertEquals(7.5, function.getX(3), 0.0001);
    }

    @Test
    void testConstructorWithSwappedBounds() {
        // Тест на корректную работу, когда xFrom > xTo
        MathFunction sqr = new SqrFunction();
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(sqr, 10.0, 0.0, 5);

        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0));
        assertEquals(0.0, function.getY(0));
        assertEquals(10.0, function.getX(4));
        assertEquals(100.0, function.getY(4));

        // Проверяем шаг
        assertEquals(2.5, function.getX(1), 0.0001);
        assertEquals(5.0, function.getX(2), 0.0001);
        assertEquals(7.5, function.getX(3), 0.0001);
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

        // Проверка на значения выше максимального X
        assertEquals(3, function.floorIndexOfX(10.0));
    }

    @Test // Тут делаем финальную проверку большинства методов
    public void TestApply() {
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

    @Test
    void testInsertBeforeHead() {
        MathFunction linearFunction = x -> 2 * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        // Вставка элемента перед головой
        function.insert(-1.0, -2.0);
        assertEquals(6, function.getCount());
        assertEquals(-1.0, function.getX(0));
        assertEquals(-2.0, function.getY(0));
    }

    @Test
    void testInsertBetweenExistingElements() {
        MathFunction linearFunction = x -> 2 * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        // Вставка нового значения между существующими
        function.insert(5.0, 11.0);
        assertEquals(5, function.getCount());
        assertEquals(5.0, function.getX(2));
        assertEquals(11.0, function.getY(2));
    }

    @Test
    void testUpdateExistingValue() {
        MathFunction linearFunction = x -> 2 * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        // Обновление значения существующего узла
        function.insert(2.0, 10.0);
        assertEquals(10.0, function.getY(1));
    }

    @Test
    void testInsertAtEnd() {
        MathFunction linearFunction = x -> 2 * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(linearFunction, 0.0, 10.0, 5);

        // Вставка значения в конец списка
        function.insert(12.0, 24.0);
        assertEquals(6, function.getCount());
        assertEquals(12.0, function.getX(5));
        assertEquals(24.0, function.getY(5));
    }

    @Test
    void testExceptionCreateEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LinkedListTabulatedFunction(new double[]{}, new double[]{});
        }, "Должно выбросить IllegalArgumentException при попытке создания таблицы с нулевым количеством точек.");
    }

    @Test
    public void testConstructorWithInsufficientPoints() {
        double[] xValues = {1.0};
        double[] yValues = {2.0};

        assertThrows(IllegalArgumentException.class, () -> {
            new LinkedListTabulatedFunction(xValues, yValues);
        }, "Должно выбросить IllegalArgumentException при создании таблицы с менее чем 2 точками.");
    }

    @Test
    public void testConstructorWithMathFunctionInsufficientPoints() {
        MathFunction linearFunction = x -> 2 * x;
        assertThrows(IllegalArgumentException.class, () -> {
            new LinkedListTabulatedFunction(linearFunction, 0.0, 1.0, 1);
        }, "Должно выбросить IllegalArgumentException при создании таблицы с менее чем 2 точками.");
    }

    @Test
    public void testGetXWithInvalidIndex() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Негативные индексы
        assertThrows(IllegalArgumentException.class, () -> {
            function.getX(-1);
        }, "Должно выбросить IllegalArgumentException для отрицательного индекса.");

        assertThrows(IllegalArgumentException.class, () -> {
            function.getX(3);
        }, "Должно выбросить IllegalArgumentException для индекса равного длине таблицы.");
    }

    @Test
    public void testGetYWithInvalidIndex() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Негативные индексы
        assertThrows(IllegalArgumentException.class, () -> {
            function.getY(-1);
        }, "Должно выбросить IllegalArgumentException для отрицательного индекса.");

        assertThrows(IllegalArgumentException.class, () -> {
            function.getY(3);
        }, "Должно выбросить IllegalArgumentException для индекса равного длине таблицы.");
    }

    @Test
    public void testSetYWithInvalidIndex() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Негативные индексы
        assertThrows(IllegalArgumentException.class, () -> {
            function.setY(-1, 5.0);
        }, "Должно выбросить IllegalArgumentException для отрицательного индекса.");

        assertThrows(IllegalArgumentException.class, () -> {
            function.setY(3, 5.0);
        }, "Должно выбросить IllegalArgumentException для индекса равного длине таблицы.");
    }

    @Test
    public void testIteratorWithWhileLoop() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        Iterator<Point> iterator = function.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(xValues[index], point.x);
            assertEquals(yValues[index], point.y);
            index++;
        }
        assertEquals(xValues.length, index, "Количество итераций не совпадает с количеством элементов.");
    }

    @Test
    public void testIteratorWithForEachLoop() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        int index = 0;
        for (Point point : function) {
            assertEquals(xValues[index], point.x);
            assertEquals(yValues[index], point.y);
            index++;
        }
        assertEquals(xValues.length, index, "Количество итераций не совпадает с количеством элементов.");
    }
}