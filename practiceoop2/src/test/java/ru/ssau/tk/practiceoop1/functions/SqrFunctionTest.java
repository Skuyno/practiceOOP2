package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {
    @Test
    public void testApply() {
        MathFunction sqr = new SqrFunction();
        assertEquals(1, sqr.apply(-1), 0.000001);
        assertEquals(0.04, sqr.apply(0.2), 0.000001);
        assertEquals(0, sqr.apply(0), 0.000001);
        assertEquals(0,000009, sqr.apply(-0.0003), 0.000001);
        assertEquals(4, sqr.apply(-2), 0.000001);


    }

}