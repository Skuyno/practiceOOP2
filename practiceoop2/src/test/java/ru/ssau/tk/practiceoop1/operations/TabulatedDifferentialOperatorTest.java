package ru.ssau.tk.practiceoop1.operations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperatorTest {
    @Test
    public void testGetFactory() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(arrayFactory);

        assertEquals(arrayFactory, operator.getFactory(), "Метод getFactory() должен возвращать установленную фабрику.");
    }

    @Test
    public void testSetFactory() {
        TabulatedFunctionFactory initialFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory newFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(initialFactory);

        operator.setFactory(newFactory);
        assertEquals(newFactory, operator.getFactory(), "Метод setFactory() должен обновить фабрику.");
    }

    @Test
    public void testApplyThrowsException() {
        DifferentialOperator<TabulatedFunction> operator = new TabulatedDifferentialOperator();
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(1.0), "Метод apply() должен выбрасывать UnsupportedOperationException.");
    }

    //Тестирование вычисления производной квадратичной функции. Квадратичная функция: y = x^2, производная: y' = 2x
    @Test
    public void testDeriveQuadraticFunction() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0}; // y = x^2

        TabulatedFunction quadraticFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        DifferentialOperator<TabulatedFunction> operator = new TabulatedDifferentialOperator(arrayFactory);

        TabulatedFunction derivative = operator.derive(quadraticFunction);

        double[] expectedDerivativeY = {1.0, 3.0, 5.0, 5.0};
        assertEquals(xValues.length, derivative.getCount(), "Количество точек производной должно совпадать с исходной функцией.");

        for (int i = 0; i < derivative.getCount(); i++) {
            assertEquals(expectedDerivativeY[i], derivative.getY(i), 1e-9, "Неверное значение производной в точке x = " + derivative.getX(i));
        }
    }

    //Тестирование вычисления производной с использованием другой фабрики (например, LinkedListTabulatedFunctionFactory).
    @Test
    public void testDeriveUsingDifferentFactory() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 2.0, 4.0}; // y = 2x, производная: y' = 2

        TabulatedFunction linearFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        DifferentialOperator<TabulatedFunction> operator = new TabulatedDifferentialOperator(linkedListFactory);

        TabulatedFunction derivative = operator.derive(linearFunction);

        double[] expectedY = {2.0, 2.0, 2.0};
        assertEquals(xValues.length, derivative.getCount(), "Количество точек производной должно совпадать с исходной функцией.");

        for (int i = 0; i < derivative.getCount(); i++) {
            assertEquals(expectedY[i], derivative.getY(i), 1e-9, "Неверное значение производной в точке x = " + derivative.getX(i));
        }
    }

    //Производная квадратичной функции с использованием фабрики по умолчанию.
    @Test
    public void testDeriveQuadraticFunctionUsingDefaultFactory() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0}; // y = x^2

        TabulatedFunction quadraticFunction = new ArrayTabulatedFunction(xValues, yValues);
        DifferentialOperator<TabulatedFunction> operator = new TabulatedDifferentialOperator();

        TabulatedFunction derivative = operator.derive(quadraticFunction);

        double[] expectedDerivativeY = {3.0, 5.0, 5.0};
        assertEquals(xValues.length, derivative.getCount(), "Количество точек производной должно совпадать с исходной функцией.");

        for (int i = 0; i < derivative.getCount(); i++) {
            assertEquals(expectedDerivativeY[i], derivative.getY(i), 1e-9, "Неверное значение производной в точке x = " + derivative.getX(i));
        }
    }

}
