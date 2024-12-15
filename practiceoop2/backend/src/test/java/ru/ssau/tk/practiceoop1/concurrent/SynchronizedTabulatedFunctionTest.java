package ru.ssau.tk.practiceoop1.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SynchronizedTabulatedFunctionTest {



    private TabulatedFunction originalFunction;
    private SynchronizedTabulatedFunction synchronizedFunction;

    @BeforeEach
    public void setUp() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        originalFunction = new LinkedListTabulatedFunction(xValues, yValues);
        synchronizedFunction = new SynchronizedTabulatedFunction(originalFunction);
    }

    @Test
    public void testGetCount() {
        assertEquals(3, synchronizedFunction.getCount());
    }

    @Test
    public void testGetX() {
        assertEquals(1.0, synchronizedFunction.getX(0));
        assertEquals(2.0, synchronizedFunction.getX(1));
        assertEquals(3.0, synchronizedFunction.getX(2));
    }

    @Test
    public void testGetY() {
        assertEquals(2.0, synchronizedFunction.getY(0));
        assertEquals(4.0, synchronizedFunction.getY(1));
        assertEquals(6.0, synchronizedFunction.getY(2));
    }

    @Test
    public void testSetY() {
        synchronizedFunction.setY(1, 8.0);
        assertEquals(8.0, synchronizedFunction.getY(1));
    }

    @Test
    public void testIndexOfX() {
        assertEquals(1, synchronizedFunction.indexOfX(2.0));
        assertEquals(-1, synchronizedFunction.indexOfX(4.0)); // Не существует
    }

    @Test
    public void testIndexOfY() {
        assertEquals(1, synchronizedFunction.indexOfY(4.0));
        assertEquals(-1, synchronizedFunction.indexOfY(5.0)); // Не существует
    }

    @Test
    public void testBounds() {
        assertEquals(1.0, synchronizedFunction.leftBound());
        assertEquals(3.0, synchronizedFunction.rightBound());
    }

    @Test
    public void testApply() {
        assertEquals(4.0, synchronizedFunction.apply(2.0));
        assertThrows(IndexOutOfBoundsException.class, () -> synchronizedFunction.apply(4.0)); // Не существует
    }

    @Test
    void testIterator() {

        // Получаем итератор
        Iterator<Point> iterator = synchronizedFunction.iterator();

        // Проверяем, что итератор корректно проходит по всем элементам
        assertTrue(iterator.hasNext());
        Point p1 = iterator.next();
        assertEquals(1.0, p1.x);
        assertEquals(2.0, p1.y);

        assertTrue(iterator.hasNext());
        Point p2 = iterator.next();
        assertEquals(2.0, p2.x);
        assertEquals(4.0, p2.y);

        assertTrue(iterator.hasNext());
        Point p3 = iterator.next();
        assertEquals(3.0, p3.x);
        assertEquals(6.0, p3.y);

        // Больше элементов нет
        assertFalse(iterator.hasNext());

        // Проверка выброса исключения при вызове next() без наличия элементов
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testDoSynchronouslyWithReturnValue() {
        // Проверка на получение количества точек
        int count = synchronizedFunction.doSynchronously(func -> func.getCount());
        assertEquals(3, count);

        // Проверка на получение значения Y по индексу
        double yValue = synchronizedFunction.doSynchronously(func -> func.getY(1));
        assertEquals(4.0, yValue);
    }

    @Test
    public void testDoSynchronouslyWithVoid() {
        // Изменение значения Y с использованием doSynchronously
        synchronizedFunction.doSynchronously(func -> {
            func.setY(0, 10.0); // Изменяем значение Y для первого элемента
            return null; // Возвращаем null, так как это операция без возвращаемого значения
        });

        // Проверка изменения
        assertEquals(10.0, synchronizedFunction.getY(0));
    }

    @Test
    public void testDoSynchronouslyWithComplexOperation() {
        // Выполняем сложную операцию: изменение значений Y и получение нового значения
        double newYValue = synchronizedFunction.doSynchronously(func -> {
            for (int i = 0; i < func.getCount(); i++) {
                func.setY(i, func.getY(i) + 1); // Увеличиваем все Y на 1
            }
            return func.getY(1); // Возвращаем новое значение Y для второго элемента
        });

        // Проверка результата
        assertEquals(5.0, newYValue); // Новый Y для индекса 1 должен быть 5.0 (4.0 + 1)
    }

    @Test
    public void testDoSynchronouslyWithInvalidAccess() {
        // Проверка на выброс исключения при обращении к несуществующему индексу
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            synchronizedFunction.doSynchronously(func -> {
                return func.getY(3); // Индекс вне диапазона
            });
        });

        assertNotNull(exception);
    }

}
