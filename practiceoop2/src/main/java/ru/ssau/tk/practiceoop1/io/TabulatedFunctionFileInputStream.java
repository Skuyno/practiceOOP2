package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.operations.TabulatedDifferentialOperator;

import java.io.*;

import static
 ru.ssau.tk.practiceoop1.io.FunctionsIO.readTabulatedFunction;
public class TabulatedFunctionFileInputStream {
    public static void main(String[] args){
        try (
                FileInputStream fileInputStream = new FileInputStream("input/binary_function.bin");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ){
            TabulatedFunction tabulatedFunction = readTabulatedFunction(bufferedInputStream, new ArrayTabulatedFunctionFactory());
            System.out.println(tabulatedFunction.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Введите размер и значения функции");
        try {
            InputStreamReader consoleInputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedConsoleInputStream = new BufferedReader(consoleInputStreamReader);
            TabulatedFunction tabulatedFunction = readTabulatedFunction(bufferedConsoleInputStream, new LinkedListTabulatedFunctionFactory());

            TabulatedDifferentialOperator DifferentialOperator = new TabulatedDifferentialOperator();
            TabulatedFunction TabulatedFunctionDerivative = DifferentialOperator.derive(tabulatedFunction);

            System.out.println(TabulatedFunctionDerivative.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
