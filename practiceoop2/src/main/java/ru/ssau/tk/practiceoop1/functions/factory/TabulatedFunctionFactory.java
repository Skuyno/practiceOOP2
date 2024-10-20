package ru.ssau.tk.practiceoop1.functions.factory;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
}
