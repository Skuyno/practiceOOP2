package ru.ssau.tk.practiceoop1.functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected double[] xValues;
    protected double[] yValues;
    protected int count;

    // Метод поиска индекса максимального значения x, которое меньше заданного x
    protected abstract int floorIndexOfX(double x);

    // Экстраполяция слева
    protected abstract double extrapolateLeft(double x);

    // Экстраполяция справа
    protected abstract double extrapolateRight(double x);

    // Интерполяция с указанием индекса интервала
    protected abstract double interpolate(double x, int floorIndex);

    // Интерполяция между двумя точками
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    // Метод для вычисления значения по заданному x
    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index);
            } else {
                int floorIndex = floorIndexOfX(x);
                return interpolate(x, floorIndex);
            }
        }
    }

}
