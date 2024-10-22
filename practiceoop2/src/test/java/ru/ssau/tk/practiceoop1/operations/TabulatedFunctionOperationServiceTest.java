package ru.ssau.tk.practiceoop1.operations;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.exceptions.InconsistentFunctionsException;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TabulatedFunctionOperationServiceTest {

    @Test
    public void testGetFactory() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        assertEquals(arrayFactory, service.getFactory(), "Метод getFactory() должен возвращать установленную фабрику.");
    }

    @Test
    public void testSetFactory() {
        TabulatedFunctionFactory initialFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory newFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(initialFactory);

        service.setFactory(newFactory);
        assertEquals(newFactory, service.getFactory(), "Метод setFactory() должен обновить фабрику.");
    }

    @Test
    public void testAsPoints() {
        TabulatedFunction function = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(3, points.length);
        assertEquals(1.0, points[0].x);
        assertEquals(10.0, points[0].y);
        assertEquals(2.0, points[1].x);
        assertEquals(20.0, points[1].y);
        assertEquals(3.0, points[2].x);
        assertEquals(30.0, points[2].y);
    }

    @Test
    public void testAddition() throws InconsistentFunctionsException {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{5.0, 15.0, 25.0});

        TabulatedFunction result = new TabulatedFunctionOperationService().add(function1, function2);

        assertEquals(3, result.getCount());
        assertEquals(1.0, result.getX(0));
        assertEquals(15.0, result.getY(0)); // 10 + 5
        assertEquals(2.0, result.getX(1));
        assertEquals(35.0, result.getY(1)); // 20 + 15
        assertEquals(3.0, result.getX(2));
        assertEquals(55.0, result.getY(2)); // 30 + 25
    }

    @Test
    public void testSubtraction() throws InconsistentFunctionsException {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{5.0, 15.0, 25.0});

        TabulatedFunction result = new TabulatedFunctionOperationService().subtract(function1, function2);

        assertEquals(3, result.getCount());
        assertEquals(1.0, result.getX(0));
        assertEquals(5.0, result.getY(0)); // 10 - 5
        assertEquals(2.0, result.getX(1));
        assertEquals(5.0, result.getY(1)); // 20 - 15
        assertEquals(3.0, result.getX(2));
        assertEquals(5.0, result.getY(2)); // 30 - 25
    }

    @Test
    public void testMultiply() throws InconsistentFunctionsException {
        TabulatedFunction function1 = new ArrayTabulatedFunction(
                new double[]{1.0, 2.0, 3.0},
                new double[]{2.0, 4.0, 6.0}
        );
        TabulatedFunction function2 = new ArrayTabulatedFunction(
                new double[]{1.0, 2.0, 3.0},
                new double[]{3.0, 5.0, 7.0}
        );

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction result = service.multiply(function1, function2);

        assertEquals(3, result.getCount());

        assertEquals(1.0, result.getX(0));
        assertEquals(6.0, result.getY(0)); // 2 * 3
        assertEquals(2.0, result.getX(1));
        assertEquals(20.0, result.getY(1)); // 4 * 5
        assertEquals(3.0, result.getX(2));
        assertEquals(42.0, result.getY(2)); // 6 * 7
    }

    @Test
    public void testDivide() throws InconsistentFunctionsException {
        TabulatedFunction function1 = new ArrayTabulatedFunction(
                new double[]{1.0, 2.0, 3.0},
                new double[]{10.0, 20.0, 30.0}
        );
        TabulatedFunction function2 = new ArrayTabulatedFunction(
                new double[]{1.0, 2.0, 3.0},
                new double[]{2.0, 4.0, 5.0}
        );

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction result = service.divide(function1, function2);

        assertEquals(3, result.getCount());

        assertEquals(1.0, result.getX(0));
        assertEquals(5.0, result.getY(0)); // 10 / 2
        assertEquals(2.0, result.getX(1));
        assertEquals(5.0, result.getY(1)); // 20 / 4
        assertEquals(3.0, result.getX(2));
        assertEquals(6.0, result.getY(2)); // 30 / 5
    }

    @Test
    public void testInconsistentFunctions() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1.0, 2.0}, new double[]{10.0, 20.0});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1.0}, new double[]{5.0});

        assertThrows(InconsistentFunctionsException.class, () -> {
            new TabulatedFunctionOperationService().add(function1, function2);
        });
    }
}
