package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.concurrent.SynchronizedTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;



public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        int pLength = points.length;
        double[] xValues = new double[pLength];
        double[] yValues = new double[pLength];

        for (int i = 0; i < pLength - 1; i++) {
            xValues[i] = points[i].x;
            yValues[i] = (points[i + 1].y - points[i].y) / (points[i + 1].x - points[i].x);
        }
        xValues[pLength-1] = points[pLength - 1].x;
        yValues[pLength - 1] = yValues[pLength - 2];

        return factory.create(xValues, yValues);
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction function) {
        // Проверяем, является ли функция уже синхронизированной
        if (function instanceof SynchronizedTabulatedFunction) {
            // Если да, просто вызываем derive() на данной функции
            return derive(function);
        } else {
            // Если нет, оборачиваем функцию в синхронизированную обертку
            SynchronizedTabulatedFunction synchronizedFunction = new SynchronizedTabulatedFunction(function);

            // Вызываем doSynchronously() с операцией вычисления производной
            return synchronizedFunction.doSynchronously(f -> derive(f));
        }
    }
}
