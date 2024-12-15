package ru.ssau.tk.practiceoop1.functions;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.StrictTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnmodifiableTabulatedFunction;

import static org.junit.jupiter.api.Assertions.*;

public class WrapperFunctionTest {

    @Test
    public void testStrictWrappedInUnmodifiable() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        StrictTabulatedFunction strict = new StrictTabulatedFunction(original);
        UnmodifiableTabulatedFunction unmodifiableStrict = new UnmodifiableTabulatedFunction(strict);

        // Проверка значений
        assertEquals(0, unmodifiableStrict.getY(0));
        assertEquals(1, unmodifiableStrict.getY(1));
        assertEquals(4, unmodifiableStrict.getY(2));

        // Проверка размера
        assertEquals(3, unmodifiableStrict.getCount());

        // Проверка apply
        assertEquals(1, unmodifiableStrict.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrict.setY(0, 10));

        // Проверка исключения при попытке интерполяции
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrict.apply(1.5));
    }

    @Test
    public void testUnmodifiableWrappedInStrict() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});
        UnmodifiableTabulatedFunction unmodifiable = new UnmodifiableTabulatedFunction(original);
        StrictTabulatedFunction strictUnmodifiable = new StrictTabulatedFunction(unmodifiable);

        // Проверка значений
        assertEquals(0, strictUnmodifiable.getY(0));
        assertEquals(1, strictUnmodifiable.getY(1));
        assertEquals(4, strictUnmodifiable.getY(2));

        // Проверка размера
        assertEquals(3, strictUnmodifiable.getCount());

        // Проверка apply
        assertEquals(1, strictUnmodifiable.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiable.setY(0, 10));

        // Проверка исключения при попытке интерполяции
        assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiable.apply(1.5));
    }

    @Test
    public void testDoubleWrapping() {
        TabulatedFunction original = new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4});

        // Оборачиваем сначала в Unmodifiable, затем в Strict
        UnmodifiableTabulatedFunction unmodifiable = new UnmodifiableTabulatedFunction(original);
        StrictTabulatedFunction doubleWrapped = new StrictTabulatedFunction(unmodifiable);

        // Проверка значений
        assertEquals(0, doubleWrapped.getY(0));
        assertEquals(1, doubleWrapped.getY(1));
        assertEquals(4, doubleWrapped.getY(2));

        // Проверка размера
        assertEquals(3, doubleWrapped.getCount());

        // Проверка apply
        assertEquals(1, doubleWrapped.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> doubleWrapped.setY(0, 10));

        // Проверка исключения при попытке интерполяции
        assertThrows(UnsupportedOperationException.class, () -> doubleWrapped.apply(1.5));

        // Оборачиваем сначала в Strict, затем в Unmodifiable
        StrictTabulatedFunction strict = new StrictTabulatedFunction(original);
        UnmodifiableTabulatedFunction doubleWrappedReverse = new UnmodifiableTabulatedFunction(strict);

        // Повторяем проверки для обратной обертки
        assertEquals(0, doubleWrappedReverse.getY(0));
        assertEquals(1, doubleWrappedReverse.getY(1));
        assertEquals(4, doubleWrappedReverse.getY(2));
        // Проверка размера
        assertEquals(3, doubleWrappedReverse.getCount());

        // Проверка apply
        assertEquals(1, doubleWrappedReverse.apply(1));

        // Проверка исключения при попытке изменить значение
        assertThrows(UnsupportedOperationException.class, () -> doubleWrappedReverse.setY(0, 10));

        // Проверка исключения при попытке интерполяции
        assertThrows(UnsupportedOperationException.class, () -> doubleWrappedReverse.apply(1.5));
    }
}

