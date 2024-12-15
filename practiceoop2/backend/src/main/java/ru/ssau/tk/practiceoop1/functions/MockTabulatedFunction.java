package ru.ssau.tk.practiceoop1.functions;

import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import java.util.Iterator;

public class MockTabulatedFunction extends AbstractTabulatedFunction {

    private final double x0 = 0.0;
    private final double x1 = 1.0;
    private double y0 = 0.0;
    private double y1 = 1.0;

    public MockTabulatedFunction() {
        xValues = new double[]{x0, x1};
        yValues = new double[]{y0, y1};
        count = 2; // Всегда 2 точки
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x >= x1) {
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    protected double extrapolateLeft(double x) {
        return y0;
    }

    @Override
    protected double extrapolateRight(double x) {
        return y1;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        //проверка
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("x находится вне интервала интерполирования.");
        }
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        if (index == 0) {
            return x0;
        } else if (index == 1) {
            return x1;
        }
        return 0;
    }

    @Override
    public double getY(int index) {
        if (index == 0) {
            return y0;
        } else if (index == 1) {
            return y1;
        }
        return 0;
    }

    @Override
    public void setY(int index, double value) {
        if (index == 0) {
            y0 = value;
        } else if (index == 1) {
            y1 = value;
        }
    }

    @Override
    public int indexOfX(double x) {
        if (x == x0) {
            return 0;
        } else if (x == x1) {
            return 1;
        }
        return -1; // Возвращаем -1, если x не найден
    }

    @Override
    public int indexOfY(double y) {
        if (y == y0) {
            return 0;
        } else if (y == y1) {
            return 1;
        }
        return -1; // Возвращаем -1, если y не найден
    }

    @Override
    public double leftBound() {
        return x0;
    }

    @Override
    public double rightBound() {
        return x1;
    }

    @Override
    public Iterator<Point> iterator() {
        throw new UnsupportedOperationException();
    }
}
