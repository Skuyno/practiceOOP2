package ru.ssau.tk.practiceoop1.db.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionEntityTest {

    @Test
    void testMathFunctionEntityConstructor() {
        MathFunctionEntity function = new MathFunctionEntity(1L, "Test Function", 3, 0.0, 10.0, null);
        assertEquals(1L, function.getId());
        assertEquals("Test Function", function.getName());
        assertEquals(3, function.getCount());
        assertEquals(0.0, function.getX_from());
        assertEquals(10.0, function.getX_to());
    }

    @Test
    void testMathFunctionEntitySettersAndGetters() {
        MathFunctionEntity function = new MathFunctionEntity();
        function.setId(2L);
        function.setName("Updated Function");
        function.setCount(5);
        function.setX_from(1.0);
        function.setX_to(20.0);

        assertEquals(2L, function.getId());
        assertEquals("Updated Function", function.getName());
        assertEquals(5, function.getCount());
        assertEquals(1.0, function.getX_from());
        assertEquals(20.0, function.getX_to());
    }
}