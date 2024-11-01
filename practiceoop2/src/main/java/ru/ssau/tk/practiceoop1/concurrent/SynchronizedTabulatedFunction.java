package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.operations.TabulatedFunctionOperationService;

import java.util.Iterator;
import java.util.NoSuchElementException;

class SynchronizedTabulatedFunction implements TabulatedFunction {
    private final TabulatedFunction function;

    public SynchronizedTabulatedFunction(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public synchronized int getCount() {
        return function.getCount();
    }

    @Override
    public synchronized double getX(int index) {
        return function.getX(index);
    }

    @Override
    public synchronized double getY(int index) {
        return function.getY(index);
    }

    @Override
    public synchronized void setY(int index, double x) {
        function.setY(index, x);
    }
    @Override
    public synchronized int indexOfX(double x){
        return function.indexOfX(x);
    }
    @Override
    public synchronized int indexOfY(double y) {
        return function.indexOfY(y);
    }
    @Override
    public synchronized double leftBound() {
        return function.leftBound();
    }
    @Override
    public synchronized double rightBound(){
        return function.rightBound();
    }

    @Override
    public Iterator<Point> iterator() {
        Point[] points;
        synchronized (function) {
            points = TabulatedFunctionOperationService.asPoints(function);
        }

        return new Iterator<Point>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < points.length;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Больше нет элементов для итерации.");
                }
                return points[current++];
            }
        };
    }

    @Override
    public double apply(double x) {
        synchronized (function) {
            if (x < leftBound() || x > rightBound()) {
                throw new IndexOutOfBoundsException("x is out of bounds");
            }
            return function.apply(x);
        }
    }


    // Внутренний интерфейс Operation
    public interface Operation<T> {
        T apply(SynchronizedTabulatedFunction function);
    }

    // Метод doSynchronously
    public <T> T doSynchronously(Operation<? extends T> operation) {
        synchronized (this) {
            return operation.apply(this);
        }
    }
}