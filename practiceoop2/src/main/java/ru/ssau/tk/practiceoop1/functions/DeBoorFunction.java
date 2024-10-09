package ru.ssau.tk.practiceoop1.functions;

public class DeBoorFunction implements MathFunction {
    private final double[] controlPoints;
    private final double[] knots;
    private final int degree;

    /**
     * Конструктор класса DeBoorFunction.
     *
     * @param controlPoints Массив контрольных точек
     * @param knots         Узловой вектор
     * @param degree        Степень B-сплайна
     */
    public DeBoorFunction(double[] controlPoints, double[] knots, int degree) {
        this.controlPoints = controlPoints.clone();
        this.knots = knots.clone();
        this.degree = degree;
    }

    /**
     * Проверяет, является ли массив неубывающим.
     *
     * @param array Массив для проверки
     * @return true, если массив неубывающий, иначе false
     */
    private boolean isNonDecreasing(double[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Находит индекс узла, такой что knots[index] <= x < knots[index + 1]
     *
     * @param x Точка, для которой ищется узел
     * @return Индекс узла
     */
    private int findKnotIndex(double x) {
        for (int i = degree; i < knots.length - degree - 1; i++) {
            if (x >= knots[i] && x < knots[i + 1]) {
                return i;
            }
        }
        // Если x == knots[knots.length - degree -1], возвращаем последний интервал
        return knots.length - degree - 2;
    }

    /**
     * Реализует алгоритм Де Бура для вычисления значения B-сплайна в точке x.
     *
     * @param x Точка, в которой вычисляется сплайн
     * @return Значение сплайна в точке x
     */
    @Override
    public double apply(double x) {
        int k = degree;
        int i = findKnotIndex(x);

        // Инициализация d[] = {P_{i - k}, ..., P_i}
        double[] d = new double[k + 1];
        for (int j = 0; j <= k; j++) {
            d[j] = controlPoints[i - k + j];
        }

        // Выполнение итераций алгоритма Де Бура
        for (int r = 1; r <= k; r++) {
            for (int j = k; j >= r; j--) {
                double alphaNumerator = x - knots[i - k + j];
                double alphaDenominator = knots[i + 1 + j - r] - knots[i - k + j];
                double alpha = alphaNumerator / alphaDenominator;
                d[j] = (1.0 - alpha) * d[j - 1] + alpha * d[j];
            }
        }

        return d[k];
    }
}
