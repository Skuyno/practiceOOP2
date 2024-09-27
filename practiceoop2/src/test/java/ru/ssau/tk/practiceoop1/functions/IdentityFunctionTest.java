package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {
    @Test
    public void testApply() {
        MathFunction identity = new IdentityFunction();

        assertEquals(23412867.0, identity.apply(23412867.0), 0.000001);
        assertEquals(0.00000214, identity.apply(0.00000214), 0.000001);
        assertEquals(0, identity.apply(0), 0.000001);
        assertEquals(-0.00000395, identity.apply(-0.00000395), 0.000001);
        assertEquals(-32134267.0, identity.apply(-32134267.0), 0.000001);
    }

}