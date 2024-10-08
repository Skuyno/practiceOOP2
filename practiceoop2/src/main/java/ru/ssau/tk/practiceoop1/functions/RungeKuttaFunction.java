package ru.ssau.tk.practiceoop1.functions;

public class RungeKuttaFunction implements MathFunction {
    // Метод для вычисления значения функции f(x, y)
    public static double f(double x, double y) {
        return x + y;  // Задайте свою функцию f(x, y)
    }

    // Метод Рунге-Кутты 4-го порядка
    public static double rungeKutta(double x0, double y0, double x, double h) {
        int n = (int) ((x - x0) / h);
        double k1, k2, k3, k4;
        double y = y0;

        for (int i = 1; i <= n; i++) {
            k1 = h * f(x0, y);
            k2 = h * f(x0 + 0.5 * h, y + 0.5 * k1);
            k3 = h * f(x0 + 0.5 * h, y + 0.5 * k2);
            k4 = h * f(x0 + h, y + k3);

            // Обновляем следующее значение y
            y = y + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4);

            // Обновляем следующее значение x
            x0 = x0 + h;
        }

        return y;
    }
}
