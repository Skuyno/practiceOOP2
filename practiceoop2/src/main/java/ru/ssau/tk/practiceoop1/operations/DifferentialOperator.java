package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.functions.MathFunction;

public interface DifferentialOperator<T> extends MathFunction {
    T derive(T function);
}
