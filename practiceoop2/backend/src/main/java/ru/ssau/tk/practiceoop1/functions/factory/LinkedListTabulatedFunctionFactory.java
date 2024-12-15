package ru.ssau.tk.practiceoop1.functions.factory;

import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues,yValues);
    }
}
