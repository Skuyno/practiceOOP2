package ru.ssau.tk.practiceoop1.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.practiceoop1.functions.factory.TabulatedFunctionFactory;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsIOTest {

    private static final Path TEMP_DIR = Paths.get("temp");

    private TabulatedFunctionFactory arrayFactory;
    private TabulatedFunctionFactory linkedListFactory;

    @BeforeEach
    public void setUp() throws IOException {
        Files.createDirectories(TEMP_DIR);
        arrayFactory = new ArrayTabulatedFunctionFactory();
        linkedListFactory = new LinkedListTabulatedFunctionFactory();
    }

    @AfterEach
    public void tearDown() throws IOException {
        try (Stream<Path> files = Files.walk(TEMP_DIR)) {
            files.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (!file.delete()) {
                            System.err.println("Не удалось удалить файл: " + file.getPath());
                        }
                    });
        }
    }

    @Test
    void testWriteAndReadTabulatedFunctionText() throws IOException {
        Path tempFile = TEMP_DIR.resolve("test_function_text.txt");

        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};
        TabulatedFunction function = arrayFactory.create(xValues, yValues);

        // Запись функции в текстовый файл
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            FunctionsIO.writeTabulatedFunction(writer, function);
        }

        // Чтение функции из текстового файла
        TabulatedFunction readFunction;
        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            readFunction = FunctionsIO.readTabulatedFunction(reader, arrayFactory);
        }

        assertEquals(function.getCount(), readFunction.getCount(), "Количество точек должно совпадать");
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9, "Значения x должны совпадать");
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9, "Значения y должны совпадать");
        }
    }

    @Test
    void testWriteAndReadTabulatedFunctionBinary() throws IOException {
        Path tempFile = TEMP_DIR.resolve("test_function_binary.bin");

        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};
        TabulatedFunction function = linkedListFactory.create(xValues, yValues);

        // Запись функции в бинарный файл
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.writeTabulatedFunction(bos, function);
        }

        // Чтение функции из бинарного файла
        TabulatedFunction readFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            readFunction = FunctionsIO.readTabulatedFunction(bis, linkedListFactory);
        }

        assertEquals(function.getCount(), readFunction.getCount(), "Количество точек должно совпадать");
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9, "Значения x должны совпадать");
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9, "Значения y должны совпадать");
        }
    }

    @Test
    void testSerializeAndDeserializeFunction() throws IOException, ClassNotFoundException {
        Path tempFile = TEMP_DIR.resolve("test_function_serialized.bin");

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        TabulatedFunction function = arrayFactory.create(xValues, yValues);

        // Сериализация функции
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.serialize(bos, function);
        }

        // Десериализация функции
        TabulatedFunction deserializedFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            deserializedFunction = FunctionsIO.deserialize(bis);
        }

        assertEquals(function.getCount(), deserializedFunction.getCount(), "Количество точек должно совпадать");
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), deserializedFunction.getX(i), 1e-9, "Значения x должны совпадать");
            assertEquals(function.getY(i), deserializedFunction.getY(i), 1e-9, "Значения y должны совпадать");
        }
    }

    @Test
    void testDeserializeWithInvalidData() throws IOException {
        Path tempFile = TEMP_DIR.resolve("invalid_serialized_function.ser");

        // Запись некорректных данных в файл
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("This is not a serialized object");
        }

        // Попытка десериализации некорректных данных должна выбросить IOException или ClassNotFoundException
        assertThrows(IOException.class, () -> {
            try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
                FunctionsIO.deserialize(bis);
            }
        }, "Десериализация некорректных данных должна выбросить IOException");
    }

    @Test
    void testReadTabulatedFunctionBinaryWithInvalidData() throws IOException {
        Path tempFile = TEMP_DIR.resolve("invalid_binary_function.dat");

        // Запись некорректных бинарных данных
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            bos.write(new byte[]{0, 1, 2, 3, 4});
        }

        // Попытка чтения некорректных данных должна выбросить IOException
        assertThrows(IOException.class, () -> {
            try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
                FunctionsIO.readTabulatedFunction(bis, arrayFactory);
            }
        }, "Чтение некорректных бинарных данных должно выбросить IOException");
    }

    @Test
    void testReadTabulatedFunctionTextWithInvalidData() throws IOException {
        Path tempFile = TEMP_DIR.resolve("invalid_text_function.txt");

        // Запись некорректных текстовых данных
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("invalid data");
        }

        // Попытка чтения некорректных данных должна выбросить IOException
        assertThrows(IOException.class, () -> {
            try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
                FunctionsIO.readTabulatedFunction(reader, arrayFactory);
            }
        }, "Чтение некорректных текстовых данных должно выбросить IOException");
    }
}