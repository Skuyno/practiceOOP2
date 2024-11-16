package ru.ssau.tk.practiceoop1.db.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointEntityTest {

    @Test
    void testPointEntityConstructor() {
        MathFunctionEntity function = new MathFunctionEntity();
        PointEntity point = new PointEntity(1L, function, 2.0, 3.0);

        assertEquals(1L, point.getId());
        assertEquals(function, point.getFunction());
        assertEquals(2.0, point.getX());
        assertEquals(3.0, point.getY());
    }

    @Test
    void testPointEntitySettersAndGetters() {
        MathFunctionEntity function = new MathFunctionEntity();
        PointEntity point = new PointEntity();
        point.setId(2L);
        point.setFunction(function);
        point.setX(5.0);
        point.setY(7.0);

        assertEquals(2L, point.getId());
        assertEquals(function, point.getFunction());
        assertEquals(5.0, point.getX());
        assertEquals(7.0, point.getY());
    }
}