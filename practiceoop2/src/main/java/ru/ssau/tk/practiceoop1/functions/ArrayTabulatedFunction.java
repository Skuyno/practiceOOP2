package ru.ssau.tk.practiceoop1.functions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import java.util.NoSuchElementException;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable, Serializable {
    @Serial
    private static final long serialVersionUID = -1585512809164835865L;

    // Конструктор с двумя параметрами
    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2) {
            throw new IllegalArgumentException("Длина таблицы меньше минимальной (2 точки).");
        }

        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);

        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    // Конструктор с четырьмя параметрами
    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Длина таблицы меньше минимальной (2 точки).");
        }

        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        double step = (xTo - xFrom) / (count - 1);

        for (int i = 0; i < count; i++) {
            xValues[i] = xFrom + i * step;
            yValues[i] = source.apply(xValues[i]);
        }
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x <= leftBound()) {
            return 0;
        }
        for (int i = 1; i < count; i++) {
            if (xValues[i] > x) {
                return i - 1;
            }
        }
        return count - 1; // Если x больше всех значений
    }

    @Override
    protected double extrapolateLeft(double x) {
        return yValues[0]; // Если массив из одного элемента
    }

    @Override
    protected double extrapolateRight(double x) {
        return yValues[count - 1]; // Если массив из одного элемента
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        //проверка
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("x находится вне интервала интерполирования.");
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        yValues[index] = value;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1; // Не найдено
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1; // Не найдено
    }

    @Override
    public void insert(double x, double y) {
        int tempX;
        if ((tempX = indexOfX(x)) != -1) {
            setY(tempX, y);
        } else {
            double[] xValuesTemp = new double[count + 1];
            double[] yValuesTemp = new double[count + 1];
            // Позиция вставки должна быть на 1 элемент больше
            tempX = floorIndexOfX(x) + 1;

            System.arraycopy(xValues, 0, xValuesTemp, 0, tempX);
            System.arraycopy(yValues, 0, yValuesTemp, 0, tempX);

            xValuesTemp[tempX] = x;
            yValuesTemp[tempX] = y;

            System.arraycopy(xValues, tempX, xValuesTemp, tempX + 1, count - tempX);
            System.arraycopy(yValues, tempX, yValuesTemp, tempX + 1, count - tempX);

            xValues = xValuesTemp;
            yValues = yValuesTemp;
            count++;
        }
    }

    @Override
    public void remove(int index) {
        if (count == 2) {
            throw new IllegalStateException("Невозможно удалить точку, кол-во элементов должно быть хотя бы 2");
        }
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Индекс выходит за допустимые пределы.");
        }

        double[] newXValues = new double[count - 1];
        double[] newYValues = new double[count - 1];

        System.arraycopy(xValues, 0, newXValues, 0, index);
        System.arraycopy(xValues, index + 1, newXValues, index, count - index - 1);

        System.arraycopy(yValues, 0, newYValues, 0, index);
        System.arraycopy(yValues, index + 1, newYValues, index, count - index - 1);

        this.xValues = newXValues;
        this.yValues = newYValues;
        this.count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Больше нет элементов для итерации.");
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }

}


