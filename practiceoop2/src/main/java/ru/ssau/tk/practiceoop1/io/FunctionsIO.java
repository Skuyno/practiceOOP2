package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import java.io.*;

public final class FunctionsIO {
    // Приватный конструктор, чтобы предотвратить создание экземпляров
    private FunctionsIO() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeInt(function.getCount());
        for (Point point : function) {
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }

        dataOutputStream.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int count = dataInputStream.readInt();
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        for (int i = 0; i < count; i++) {
            xValues[i] = dataInputStream.readDouble();
            yValues[i] = dataInputStream.readDouble();
        }
        return factory.create(xValues, yValues);
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);
        Object obj = objectInputStream.readObject();
        if (!(obj instanceof TabulatedFunction)) {
            throw new IOException("Десериализованный объект не является экземпляром TabulatedFunction.");
        }
        return (TabulatedFunction) obj;
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);

        // Записываем количество точек
        printWriter.println(function.getCount());

        // Записываем каждую пару значений x и y
        for (int i = 0; i < function.getCount(); i++) {
            printWriter.printf("%f %fn", function.getX(i), function.getY(i));
        }

        // Пробрасываем данные из буфера
        printWriter.flush();
    }
}
