package ru.ssau.tk.practiceoop1.functions;

public interface MathFunction {
    double apply(double x);

    default CompositeFunction andThen(MathFunction afterFunction) {
        return (double x) -> afterFunction.apply(this.apply(x));
    }
}
