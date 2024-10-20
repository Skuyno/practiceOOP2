package ru.ssau.tk.practiceoop1.functions.factory;

import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}
