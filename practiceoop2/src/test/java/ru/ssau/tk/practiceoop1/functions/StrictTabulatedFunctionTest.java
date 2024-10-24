package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {

    private StrictTabulatedFunction strictArrayFunction;
    private StrictTabulatedFunction strictLinkedListFunction;

    @BeforeEach
    void setUp() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};

        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);

        strictArrayFunction = new StrictTabulatedFunction(arrayFunction);
        strictLinkedListFunction = new StrictTabulatedFunction(linkedListFunction);
    }

    @Test
    void testApplyExactPoints_Array() {
        assertEquals(2.0, strictArrayFunction.apply(1.0));
        assertEquals(4.0, strictArrayFunction.apply(2.0));
        assertEquals(6.0, strictArrayFunction.apply(3.0));
    }

    @Test
    void testApplyExactPoints_LinkedList() {
        assertEquals(2.0, strictLinkedListFunction.apply(1.0));
        assertEquals(4.0, strictLinkedListFunction.apply(2.0));
        assertEquals(6.0, strictLinkedListFunction.apply(3.0));
    }

    @Test
    void testApplyNonExistingPoint_Array() {
        assertThrows(UnsupportedOperationException.class, () -> strictArrayFunction.apply(1.5));
        assertThrows(UnsupportedOperationException.class, () -> strictArrayFunction.apply(0.0));
        assertThrows(UnsupportedOperationException.class, () -> strictArrayFunction.apply(4.0));
    }

    @Test
    void testApplyNonExistingPoint_LinkedList() {
        assertThrows(UnsupportedOperationException.class, () -> strictLinkedListFunction.apply(1.5));
        assertThrows(UnsupportedOperationException.class, () -> strictLinkedListFunction.apply(0.0));
        assertThrows(UnsupportedOperationException.class, () -> strictLinkedListFunction.apply(4.0));
    }

    @Test
    void testGetCount() {
        assertEquals(3, strictArrayFunction.getCount());
        assertEquals(3, strictLinkedListFunction.getCount());
    }

    @Test
    void testGetX() {
        assertEquals(1.0, strictArrayFunction.getX(0));
        assertEquals(2.0, strictArrayFunction.getX(1));
        assertEquals(3.0, strictArrayFunction.getX(2));

        assertEquals(1.0, strictLinkedListFunction.getX(0));
        assertEquals(2.0, strictLinkedListFunction.getX(1));
        assertEquals(3.0, strictLinkedListFunction.getX(2));
    }

    @Test
    void testGetY() {
        assertEquals(2.0, strictArrayFunction.getY(0));
        assertEquals(4.0, strictArrayFunction.getY(1));
        assertEquals(6.0, strictArrayFunction.getY(2));

        assertEquals(2.0, strictLinkedListFunction.getY(0));
        assertEquals(4.0, strictLinkedListFunction.getY(1));
        assertEquals(6.0, strictLinkedListFunction.getY(2));
    }

    @Test
    void testSetY() {
        strictArrayFunction.setY(1, 5.0);
        assertEquals(5.0, strictArrayFunction.getY(1));

        strictLinkedListFunction.setY(2, 7.0);
        assertEquals(7.0, strictLinkedListFunction.getY(2));
    }

    @Test
    void testIndexOfX() {
        assertEquals(0, strictArrayFunction.indexOfX(1.0));
        assertEquals(1, strictArrayFunction.indexOfX(2.0));
        assertEquals(2, strictArrayFunction.indexOfX(3.0));
        assertEquals(-1, strictArrayFunction.indexOfX(1.5));

        assertEquals(0, strictLinkedListFunction.indexOfX(1.0));
        assertEquals(1, strictLinkedListFunction.indexOfX(2.0));
        assertEquals(2, strictLinkedListFunction.indexOfX(3.0));
        assertEquals(-1, strictLinkedListFunction.indexOfX(2.5));
    }

    @Test
    void testIndexOfY() {
        assertEquals(0, strictArrayFunction.indexOfY(2.0));
        assertEquals(1, strictArrayFunction.indexOfY(4.0));
        assertEquals(2, strictArrayFunction.indexOfY(6.0));
        assertEquals(-1, strictArrayFunction.indexOfY(5.0));

        assertEquals(0, strictLinkedListFunction.indexOfY(2.0));
        assertEquals(1, strictLinkedListFunction.indexOfY(4.0));
        assertEquals(2, strictLinkedListFunction.indexOfY(6.0));
        assertEquals(-1, strictLinkedListFunction.indexOfY(7.0));
    }

    @Test
    void testLeftBound() {
        assertEquals(1.0, strictArrayFunction.leftBound());
        assertEquals(1.0, strictLinkedListFunction.leftBound());
    }

    @Test
    void testRightBound() {
        assertEquals(3.0, strictArrayFunction.rightBound());
        assertEquals(3.0, strictLinkedListFunction.rightBound());
    }

    @Test
    void testIterator_Array() {
        Iterator<Point> iterator = strictArrayFunction.iterator();
        assertTrue(iterator.hasNext());

        Point p1 = iterator.next();
        assertEquals(1.0, p1.x);
        assertEquals(2.0, p1.y);

        Point p2 = iterator.next();
        assertEquals(2.0, p2.x);
        assertEquals(4.0, p2.y);

        Point p3 = iterator.next();
        assertEquals(3.0, p3.x);
        assertEquals(6.0, p3.y);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIterator_LinkedList() {
        Iterator<Point> iterator = strictLinkedListFunction.iterator();
        assertTrue(iterator.hasNext());

        Point p1 = iterator.next();
        assertEquals(1.0, p1.x);
        assertEquals(2.0, p1.y);

        Point p2 = iterator.next();
        assertEquals(2.0, p2.x);
        assertEquals(4.0, p2.y);

        Point p3 = iterator.next();
        assertEquals(3.0, p3.x);
        assertEquals(6.0, p3.y);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testExceptionOnInvalidIndex_Array() {
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.getX(-1));
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.getX(3));
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.getY(-1));
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.getY(3));
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.setY(-1, 0.0));
        assertThrows(IllegalArgumentException.class, () -> strictArrayFunction.setY(3, 0.0));
    }

    @Test
    void testExceptionOnInvalidIndex_LinkedList() {
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.getX(-1));
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.getX(3));
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.getY(-1));
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.getY(3));
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.setY(-1, 0.0));
        assertThrows(IllegalArgumentException.class, () -> strictLinkedListFunction.setY(3, 0.0));
    }
}