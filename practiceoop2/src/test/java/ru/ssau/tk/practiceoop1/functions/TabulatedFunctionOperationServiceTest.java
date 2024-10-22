package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.operations.TabulatedFunctionOperationService;

import static org.junit.jupiter.api.Assertions.*;

public class TabulatedFunctionOperationServiceTest {

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
}
