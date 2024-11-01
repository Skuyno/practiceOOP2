package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.util.concurrent.Callable;

public class IntegrationTask implements Callable<Double> {
    private final TabulatedFunction tabulatedFunction;
    private final double a;
    private final double b;
    private final int n;

    public IntegrationTask(TabulatedFunction tabulatedFunction, double a, double b, int n) {
        if (a >= b) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой.");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("Количество шагов должно быть положительным.");
        }
        this.tabulatedFunction = tabulatedFunction;
        this.a = a;
        this.b = b;
        this.n = n;
    }

    // Реализуем интегрирование с помощью метода трапеции
    @Override
    public Double call() throws Exception {
        double h = (b - a) / n;
        double integral = 0;
        for (int i = 0; i < n; i++) {
            double x0 = a + i * h;
            double x1 = a + (i + 1) * h;
            double y0 = tabulatedFunction.apply(x0);
            double y1 = tabulatedFunction.apply(x1);
            integral += (y0 + y1) * h / 2;
        }
        return integral;
    }
}
