package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.functions.MathFunction;


public class LeftSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public LeftSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return x -> (function.apply(x) - function.apply(x - step)) / step;
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException("Operation not supported");
    }
}

