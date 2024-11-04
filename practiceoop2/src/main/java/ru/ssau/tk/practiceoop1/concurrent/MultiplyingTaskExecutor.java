package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.practiceoop1.functions.UnitFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        // Создаем табулированную функцию на основе функции единицы
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 1000);

        // Коллекция для хранения задач
        Collection<MultiplyingTask> tasks = new ConcurrentLinkedQueue<>();

        // Создание и запуск потоков
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(function);
            tasks.add(task);
            new Thread(task).start();
        }

        // Главный поток, который проверяет выполнение задач
        while (!tasks.isEmpty()) {
            tasks.removeIf(MultiplyingTask::isCompleted); // Удаляем завершенные задачи
            try {
                Thread.sleep(100); // Небольшая пауза для уменьшения нагрузки на процессор
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Выводим табулированную функцию
        System.out.println("Табулированная функция после выполнения задач:");
        for (int i = 0; i < function.getCount(); i++) {
            System.out.println("x=" + function.getX(i) + ", y=" + function.getY(i));
        }
    }
}
