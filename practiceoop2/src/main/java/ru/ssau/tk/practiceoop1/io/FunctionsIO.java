package ru.ssau.tk.practiceoop1.io;

import ru.ssau.tk.practiceoop1.functions.Point;
import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

}
