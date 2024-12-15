package ru.ssau.tk.practiceoop1.functions;

public class ConstantFunction implements MathFunction {

    private final double c;

    public ConstantFunction(double c) {
        this.c = c;
    }

    @Override
    public double apply(double x) {
        return c;
    }
}
