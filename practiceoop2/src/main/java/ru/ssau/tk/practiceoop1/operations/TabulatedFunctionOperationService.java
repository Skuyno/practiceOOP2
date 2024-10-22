package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.exceptions.InconsistentFunctionsException;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory(); // или другой тип фабрики
    }

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }


    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        int count = tabulatedFunction.getCount(); // Предполагаем, что есть метод getCount()
        Point[] points = new Point[count];

        int i = 0;
        for (Point point : tabulatedFunction) { // Предполагаем, что TabulatedFunction реализует Iterable<Point>
            points[i] = point;
            i++;
        }

        return points;
    }

    ;

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) throws InconsistentFunctionsException {
        if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException("Functions have different number of points.");
        }

        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);

        double[] xValues = new double[pointsA.length];
        double[] yValues = new double[pointsA.length];

        for (int i = 0; i < pointsA.length; i++) {
            if (pointsA[i].x != pointsB[i].x) {
                throw new InconsistentFunctionsException("Functions have different x values at index " + i);
            }
            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y);
        }

        return factory.create(xValues, yValues);
    }

    public TabulatedFunction add(TabulatedFunction a, TabulatedFunction b) throws InconsistentFunctionsException {
        return doOperation(a, b, (u, v) -> u + v);
    }

    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b) throws InconsistentFunctionsException {
        return doOperation(a, b, (u, v) -> u - v);
    }

    private interface BiOperation {
        double apply(double u, double v);
    }
}
