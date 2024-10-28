package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnitFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        // Создаем табулированную функцию на основе функции единицы
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 1000);

        // Список потоков
        List<Thread> threads = new ArrayList<>();

        // Создание и запуск потоков
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(function);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        // Усыпляем текущий поток на 2 секунды
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Выводим табулированную функцию
        System.out.println("Табулированная функция после выполнения задач:");
        for (int i = 0; i < function.getCount(); i++) {
            System.out.println("x=" + function.getX(i) + ", y=" + function.getY(i));
        }
    }
}
