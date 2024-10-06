package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {
    @Test
    public void testApply() {
        MathFunction sqr = new SqrFunction();
        assertEquals(-1, sqr.apply(-1), 0.000001);
        assertEquals(0.00000214, sqr.apply(0.00000214), 0.000001);
        assertEquals(0, sqr.apply(0), 0.000001);
        assertEquals(-0.00000395, sqr.apply(-0.00000395), 0.000001);
        assertEquals(-32134267.0, sqr.apply(-32134267.0), 0.000001);


    }

}