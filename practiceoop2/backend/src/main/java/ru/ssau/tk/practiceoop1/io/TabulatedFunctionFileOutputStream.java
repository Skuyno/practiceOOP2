package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.ArrayTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static ru.ssau.tk.practiceoop1.io.FunctionsIO.writeTabulatedFunction;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) throws IOException {
        try (
                FileOutputStream fosArray = new FileOutputStream("output/array_function.bin");
                BufferedOutputStream bosArray = new BufferedOutputStream(fosArray);

                FileOutputStream fosLinkedList = new FileOutputStream("output/linked_list_function.bin");
                BufferedOutputStream bosLinkedList = new BufferedOutputStream(fosLinkedList)
        ) {
            writeTabulatedFunction(bosArray, new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{0.0, 0.25, 1.0}));
            writeTabulatedFunction(bosLinkedList, new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{2.0, 4.0, 6.0}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
