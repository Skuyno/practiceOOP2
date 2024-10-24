package ru.ssau.tk.practiceoop1.functions.factory;

import ru.ssau.tk.practiceoop1.functions.StrictTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnmodifiableTabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    default TabulatedFunction createUnmodifiable(double[] xValues, double[] yValues) {
        TabulatedFunction tabulatedFunction = create(xValues, yValues);
        return new UnmodifiableTabulatedFunction(tabulatedFunction);
    }

    default TabulatedFunction createStrict(double[] xValues, double[] yValues){
        TabulatedFunction tabulatedFunction = create(xValues, yValues);
        return new StrictTabulatedFunction(tabulatedFunction);
    }

    default TabulatedFunction createStrictUnmodifiable(double[] xValues, double[] yValues) {
        TabulatedFunction tabulatedFunction = create(xValues, yValues);
        return new StrictTabulatedFunction(new UnmodifiableTabulatedFunction(tabulatedFunction));
    }
}
