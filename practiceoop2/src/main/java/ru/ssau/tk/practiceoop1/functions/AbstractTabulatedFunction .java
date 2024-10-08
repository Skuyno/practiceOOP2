package ru.ssau.tk.practiceoop1.functions;

public abstract class AbstractTabulatedFunction {

    protected double[] xValues; // Массив значений x
    protected double[] yValues; // Массив значений y
    protected int count; // Количество значений

    // Метод поиска индекса максимального значения x, которое меньше заданного x
    protected int floorIndexOfX(double x) {
        if (xValues[0] >= x) {
            return 0; // Если все x больше заданного
        }
        for (int i = 0; i < count; i++) {
            if (xValues[i] >= x) {
                return i - 1; // Возвращаем индекс предыдущего элемента
            }
        }
        return count; // Если все x меньше заданного
    }

    // Метод экстраполяции слева
    protected abstract double extrapolateLeft(double x);

    // Метод экстраполяции справа
    protected abstract double extrapolateRight(double x);

    // Метод интерполяции с указанием индекса интервала
    protected abstract double interpolate(double x, int floorIndex);

    // Защищённый метод с реализацией интерполяции
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    // Метод apply() из интерфейса MathFunction
    public double apply(double x) {
        if (x < xValues[0]) {
            return extrapolateLeft(x);
        } else if (x > xValues[count - 1]) {
            return extrapolateRight(x);
        } else {
            int index = indexOf(x); // Предполагается, что метод indexOf реализован
            if (index != -1) {
                return getY(index); // Предполагается, что метод getY реализован
            } else {
                int floorIndex = floorIndexOfX(x);
                return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1],
                        yValues[floorIndex], yValues[floorIndex + 1]);
            }
        }
    }

    // Предполагается, что методы indexOf и getY также реализованы
    protected abstract int indexOf(double x);
    protected abstract double getY(int index);
}
