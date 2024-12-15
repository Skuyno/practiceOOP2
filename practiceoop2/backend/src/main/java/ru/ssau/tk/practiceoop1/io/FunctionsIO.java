package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
        return (TabulatedFunction) obj;
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);

        // Записываем количество точек
        printWriter.println(function.getCount());

        // Записываем каждую пару значений x и y
        for (int i = 0; i < function.getCount(); i++) {
            printWriter.printf("%.6f %.6f%n", function.getX(i), function.getY(i));

        }

        // Пробрасываем данные из буфера
        printWriter.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            // Читаем количество точек
            String line = reader.readLine();
            int count = Integer.parseInt(line.trim());

            // Создаем массивы для x и y
            double[] xValues = new double[count];
            double[] yValues = new double[count];

            // Создаем форматтер для чтения чисел с запятой
            NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

            // Читаем точки
            for (int i = 0; i < count; i++) {
                line = reader.readLine();
                String[] parts = line.split(" ");

                // Парсим x и y
                try {
                    xValues[i] = format.parse(parts[0].trim()).doubleValue();
                    yValues[i] = format.parse(parts[1].trim()).doubleValue();
                } catch (ParseException e) {
                    throw new IOException("Ошибка при парсинге числа", e);
                }
            }

            // Создаем и возвращаем функцию с использованием фабрики
            return factory.create(xValues, yValues);
        } catch (IOException | NumberFormatException e) {
            throw new IOException("Ошибка формата числа", e);
        }
    }
}
