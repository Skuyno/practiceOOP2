package ru.ssau.tk.practiceoop1.operations;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.Point;

public class TabulatedFunctionOperationService {
    public static Point[] asPoints(TabulatedFunction tabulatedFunction){
        int count = tabulatedFunction.getCount(); // Предполагаем, что есть метод getCount()
        Point[] points = new Point[count];

        int i = 0;
        for (Point point : tabulatedFunction) { // Предполагаем, что TabulatedFunction реализует Iterable<Point>
            points[i] = point;
            i++;
        }

        return points;
    };
}
