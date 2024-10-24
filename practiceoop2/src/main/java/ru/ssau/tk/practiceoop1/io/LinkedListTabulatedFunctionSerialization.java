package ru.ssau.tk.practiceoop1.io;


import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {
    public static void main(String[] args){
        try(
                FileOutputStream fileOutputStream = new FileOutputStream("output/serialized_linked_list_function.bin");
                BufferedOutputStream bufferedFileOutputStream = new BufferedOutputStream(fileOutputStream);
        )
        {
            LinkedListTabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0, 4.0}, new double[]{1.0, 4.0, 9.0, 16.0});
            TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());

            TabulatedFunction firstDerivative = differentialOperator.derive(linkedListTabulatedFunction);
            TabulatedFunction secondDerivative = differentialOperator.derive(firstDerivative);

            FunctionsIO.serialize(bufferedFileOutputStream, linkedListTabulatedFunction);
            FunctionsIO.serialize(bufferedFileOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedFileOutputStream, secondDerivative);
        }catch(IOException e){
            e.printStackTrace();
        }

        try(
                FileInputStream fileInputStream = new FileInputStream("output/serialized_linked_list_function.bin");
                BufferedInputStream bufferedFileInputStream = new BufferedInputStream(fileInputStream);
        )
        {
            System.out.println(FunctionsIO.deserialize(bufferedFileInputStream).toString());
            System.out.println(FunctionsIO.deserialize(bufferedFileInputStream).toString());
            System.out.println(FunctionsIO.deserialize(bufferedFileInputStream).toString());
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
