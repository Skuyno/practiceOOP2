package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.util.Iterator;

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
    public synchronized Iterator<Point> iterator() {
        return function.iterator();
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

}