package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) {
        // Создаем две табулированные функции
        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{1.0, 4.0, 9.0});
        TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{1.0, 4.0, 9.0});

        // Записываем функции в файлы
        try (BufferedWriter arrayWriter = new BufferedWriter(new FileWriter("C:/Users/Admin/IdeaProjects/practiceOOP2new/practiceoop2/output/array_function.txt"));
             BufferedWriter linkedListWriter = new BufferedWriter(new FileWriter("C:/Users/Admin/IdeaProjects/practiceOOP2new/practiceoop2/output/linked_list_function.txt"))) {

            FunctionsIO.writeTabulatedFunction(arrayWriter, arrayFunction);
            FunctionsIO.writeTabulatedFunction(linkedListWriter, linkedListFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}