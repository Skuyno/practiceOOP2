package ru.ssau.tk.practiceoop1.functions.factory;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TabulatedFunctionFactoryTest {

    private final double[] xValues = {1.0, 2.0, 3.0};
    private final double[] yValues = {2.0, 4.0, 6.0};

    @Test
    public void testCreateUnmodifiable() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction unmodifiableArrayFunction = arrayFactory.createUnmodifiable(xValues, yValues);

        assertEquals(3, unmodifiableArrayFunction.getCount());
        assertEquals(2.0, unmodifiableArrayFunction.getY(0));

        // Проверка, что функция действительно неизменяема
        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableArrayFunction.setY(0, 5.0);
        });

        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction unmodifiableLinkedListFunction = linkedListFactory.createUnmodifiable(xValues, yValues);

        assertEquals(3, unmodifiableLinkedListFunction.getCount());
        assertEquals(4.0, unmodifiableLinkedListFunction.getY(1));

        // Проверка на неизменяемость
        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableLinkedListFunction.setY(1, 7.0);
        });
    }

    @Test
    public void testCreateStrictUnmodifiable() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction strictUnmodifiableArrayFunction = arrayFactory.createStrictUnmodifiable(xValues, yValues);

        assertEquals(3, strictUnmodifiableArrayFunction.getCount());
        assertEquals(2.0, strictUnmodifiableArrayFunction.getY(0));

        // Проверка на неизменяемость
        assertThrows(UnsupportedOperationException.class, () -> {
            strictUnmodifiableArrayFunction.setY(0, 5.0);
        });

        // Проверка строгой модификации
        assertThrows(IllegalArgumentException.class, () -> {
            strictUnmodifiableArrayFunction.setY(0, -1.0);
        });

        // Проверка с другой фабрикой
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction strictUnmodifiableLinkedListFunction = linkedListFactory.createStrictUnmodifiable(xValues, yValues);

        assertEquals(3, strictUnmodifiableLinkedListFunction.getCount());
        assertEquals(4.0, strictUnmodifiableLinkedListFunction.getY(1));

        // Проверка на неизменяемость
        assertThrows(UnsupportedOperationException.class, () -> {
            strictUnmodifiableLinkedListFunction.setY(1, 7.0);
        });

        // Проверка строгой модификации
        assertThrows(IllegalArgumentException.class, () -> {
            strictUnmodifiableLinkedListFunction.setY(1, -1.0);
        });
    }
}
