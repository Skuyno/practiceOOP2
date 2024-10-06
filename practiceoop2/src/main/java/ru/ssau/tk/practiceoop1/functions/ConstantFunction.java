package ru.ssau.tk.practiceoop1.functions;

public class ConstantFunction implements MathFunction {
    @Override
    private final c;
    public ConstantFunction(double c){
        this.c = c;
    }
    public double apply(double x) {
        return c;
    }
}
