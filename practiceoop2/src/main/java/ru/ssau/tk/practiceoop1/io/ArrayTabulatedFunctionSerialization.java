package ru.ssau.tk.practiceoop1.io;


import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.operations.TabulatedDifferentialOperator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;



public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        // Параметры для табулированной функции
        double[] xValues = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 2.0, 0.0, 4.0, 3.0};

        // Создание табулированной функции
        ArrayTabulatedFunction originalFunction = new ArrayTabulatedFunction(xValues, yValues);

        // Создание оператора для нахождения производных
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();

        // Нахождение первой производной
        TabulatedFunction firstDerivative = differentialOperator.derive(originalFunction);

        // Нахождение второй производной
        TabulatedFunction secondDerivative = differentialOperator.derive(firstDerivative);

        // Сериализация функций в файл
        String outputPath = "output/serialized_array_functions.bin";

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath))) {
            FunctionsIO.serialize(bos, originalFunction);
            FunctionsIO.serialize(bos, firstDerivative);
            FunctionsIO.serialize(bos, secondDerivative);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
