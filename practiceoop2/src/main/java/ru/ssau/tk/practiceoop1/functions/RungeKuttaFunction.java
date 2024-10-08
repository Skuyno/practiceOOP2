//package ru.ssau.tk.practiceoop1.functions;
//
//public class RungeKuttaFunction implements MathFunction {
//// Метод Рунге-Кутты четвертого порядка
//    public static void rungeKutta(double x0, double y0, double xEnd, double h) {
//        double n = (xEnd - x0) / h; // Количество шагов
//
//        double x = x0;
//        double y = y0;
//
//        System.out.printf("%-10s %-10s\n", "x", "y");
//        System.out.printf("%-10.4f %-10.4f\n", x, y);
//
//        for (int i = 0; i < n; i++) {
//            double k1 = h * f(x, y);
//            double k2 = h * f(x + h / 2, y + k1 / 2);
//            double k3 = h * f(x + h / 2, y + k2 / 2);
//            double k4 = h * f(x + h, y + k3);
//
//            y += (k1 + 2 * k2 + 2 * k3 + k4) / 6;
//            x += h;
//
//            System.out.printf("%-10.4f %-10.4f\n", x, y);
//        }
//    }
//
//    public static void main(String[] args) {
//        double x0 = 0;     // Начальное значение x
//        double y0 = 1;     // Начальное значение y
//        double xEnd = 2;   // Конечное значение x
//        double h = 0.1;    // Шаг
//
//        rungeKutta(x0, y0, xEnd, h);
//    }
//}
