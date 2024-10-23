package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.functions.MathFunction;

public class MiddleSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public MiddleSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction() {
            @Override
            public double apply(double x) {
                return (function.apply(x + step / 2) - function.apply(x - step / 2)) / step;
            }
        };
    }

    @Override
    public double apply(double x) {
        return 0;
    }
}
