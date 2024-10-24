package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public RightSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return x -> (function.apply(x + step) - function.apply(x)) / step;
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }
}


