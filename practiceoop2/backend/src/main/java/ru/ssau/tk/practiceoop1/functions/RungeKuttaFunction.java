package ru.ssau.tk.practiceoop1.functions;

public class RungeKuttaFunction implements MathFunction {
    private final double x0; // Начальное значение x
    private final double y0; // Начальное значение y
    private final double h;   // Шаг интегрирования

    // Конструктор для инициализации начальных условий и шага
    public RungeKuttaFunction(double x0, double y0, double h) {
        this.x0 = x0;
        this.y0 = y0;
        this.h = h;
    }

    // Метод для вычисления значения функции f(x, y)
    public static double f(double x, double y) {
        return x + y;  // Задайте свою функцию f(x, y)
    }

    // Метод Рунге-Кутты 4-го порядка
    public double rungeKutta(double x) {
        double currentX = x0;
        double y = y0;

        // Определяем количество шагов
        int n = (int) Math.floor((x - currentX) / h); // Используем Math.floor для целого числа шагов

        for (int i = 0; i < n; i++) {
            double k1 = h * f(currentX, y);
            double k2 = h * f(currentX + 0.5 * h, y + 0.5 * k1);
            double k3 = h * f(currentX + 0.5 * h, y + 0.5 * k2);
            double k4 = h * f(currentX + h, y + k3);

            // Обновляем следующее значение y
            y += (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4);
            // Обновляем текущее значение x
            currentX += h;
        }

        return y;
    }

    // Метод apply для вычисления значения функции в точке x
    @Override
    public double apply(double x) {
        return rungeKutta(x);
    }
}